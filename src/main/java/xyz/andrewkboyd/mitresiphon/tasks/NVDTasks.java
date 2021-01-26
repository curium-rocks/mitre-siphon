package xyz.andrewkboyd.mitresiphon.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

import xyz.andrewkboyd.mitresiphon.tasks.helpers.interfaces.HttpModifiedCheck;
import xyz.andrewkboyd.mitresiphon.tasks.helpers.interfaces.NvdHttpFetch;

/**
 * Tasks to fetch data from NVD and publish a message when a change is detected
 */
@Component
public class NVDTasks {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final HttpModifiedCheck modifiedCheck;
    private final NvdHttpFetch fetcher;

    private static final Logger LOG = LoggerFactory.getLogger(NVDTasks.class);


    private static final String MODIFIED_URL = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.json.gz";
    private static final String RECENT_URL = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-recent.json.gz";
    private static final String COMPLETE_URL = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2021.json.gz";

    /**
     * Create task runner for fetching CVE information from NVD JSON list
     * @param template kafka template for publishing NVD data
     */
    public NVDTasks(KafkaTemplate<String, String> template,
                    HttpModifiedCheck modCheck,
                    NvdHttpFetch nvdHttpFetch) {
        kafkaTemplate = template;
        modifiedCheck = modCheck;
        fetcher = nvdHttpFetch;
    }


    private void checkAndPublish(URI resourceUri, ZonedDateTime lastFetchTime, String topic) throws IOException, ExecutionException, InterruptedException {
        if(Boolean.TRUE.equals(modifiedCheck.isNewDataAvailable(lastFetchTime, resourceUri).get())) {
            String feedResponse = fetcher.fetch(resourceUri).get();
            kafkaTemplate.send(topic, feedResponse);
        }
    }

    /**
     * Fetch the list of CVEs that have been modified
     */
    @Scheduled(fixedRate = 5000)
    public void fetchModified() throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        checkAndPublish(new URI(MODIFIED_URL), null, "nvd.cve.modified");
    }

    /**
     * Fetch the new/recently added CVEs
     */
    @Scheduled(fixedRate = 5000)
    public void fetchRecent() throws URISyntaxException, InterruptedException, ExecutionException, IOException {
        checkAndPublish(new URI(RECENT_URL), null, "nvd.cve.modified");

    }

    /**
     * Fetch the complete list of CVEs for this year
     */
    @Scheduled(fixedRate = 5000)
    public void fetchComplete() throws URISyntaxException, InterruptedException, ExecutionException, IOException {
        checkAndPublish(new URI(COMPLETE_URL), null, "nvd.cve.modified");

    }
}
