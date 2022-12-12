package rocks.curium.mitresiphon.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;
import rocks.curium.mitresiphon.generated.models.Def_cve_item;
import rocks.curium.mitresiphon.generated.models.Nvd_cve_feed_json_1_1;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.HttpModifiedCheck;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.NvdHttpFetch;

/** Tasks to fetch data from NVD and publish a message when a change is detected */
@DisallowConcurrentExecution
public class NVDFetchTask extends QuartzJobBean {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final HttpModifiedCheck modifiedCheck;
  private final NvdHttpFetch fetcher;
  private final ResourceStatDAO resourceDAO;
  private final ObjectMapper mapper;

  private static final Logger LOG = LoggerFactory.getLogger(NVDFetchTask.class);

  public static final String MODIFIED_URL =
      "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.json.gz";
  public static final String RECENT_URL =
      "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-recent.json.gz";
  public static final String COMPLETE_URL =
      "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2022.json.gz";

  public static final String URL_PARM_NAME = "NVD_FETCH_URL";
  public static final String KAFKA_TOPIC_PARM_NAME = "NVD_PUBLISH_TARGET";

  public static final String MODIFIED_KAFKA_TOPIC = "nvd.cve.modified";
  public static final String RECENT_KAFKA_TOPIC = "nvd.cve.recent";
  public static final String COMPLETE_KAFKA_TOPIC = "nvd.cve.complete";

  /**
   * Create task runner for fetching CVE information from NVD JSON list
   *
   * @param template kafka template for publishing NVD data
   */
  public NVDFetchTask(
      KafkaTemplate<String, String> template,
      HttpModifiedCheck modCheck,
      NvdHttpFetch nvdHttpFetch,
      ResourceStatDAO resource) {
    kafkaTemplate = template;
    modifiedCheck = modCheck;
    fetcher = nvdHttpFetch;
    resourceDAO = resource;
    mapper = new ObjectMapper();
  }

  private boolean checkAndPublish(URI resourceUri, ZonedDateTime lastFetchTime, String topic)
      throws IOException, ExecutionException, InterruptedException {
    if (Boolean.TRUE.equals(modifiedCheck.isNewDataAvailable(lastFetchTime, resourceUri).get())) {
      String feedResponse = fetcher.fetch(resourceUri).get();
      final Nvd_cve_feed_json_1_1 db = mapper.readValue(feedResponse, Nvd_cve_feed_json_1_1.class);
      for (final Def_cve_item item : db.getCVE_Items()) {
        kafkaTemplate.send(topic, mapper.writeValueAsString(item));
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Execute the actual job. The job data map will already have been applied as bean property values
   * by execute. The contract is exactly the same as for the standard Quartz execute method.
   *
   * @param context job context information
   * @see #execute
   */
  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOG.trace("Starting fetch nvd task");
    JobDataMap parameterMap = context.getMergedJobDataMap();
    String url = parameterMap.getString(URL_PARM_NAME);
    String kafkaTopic = parameterMap.getString(KAFKA_TOPIC_PARM_NAME);

    LOG.debug("Starting fetch for URL {}, and kafka topic {}", url, kafkaTopic);
    try {
      if (checkAndPublish(
          URI.create(url), resourceDAO.getLastAccessTimeForResource(url), kafkaTopic)) {
        LOG.info("Fetched new data from {}, published to kafka {}", url, kafkaTopic);
        resourceDAO.recordAccessTime(url, ZonedDateTime.now());
      }
    } catch (IOException | ExecutionException e) {
      throw new JobExecutionException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new JobExecutionException(e);
    }
    LOG.trace("Finished fetch nvd task");
  }
}
