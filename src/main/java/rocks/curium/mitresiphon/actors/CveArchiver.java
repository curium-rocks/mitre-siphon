package rocks.curium.mitresiphon.actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.entities.CVE;
import rocks.curium.mitresiphon.generated.models.Def_cve_item;
import rocks.curium.mitresiphon.generated.models.Nvd_cve_feed_json_1_1;
import rocks.curium.mitresiphon.tasks.NVDFetchTask;

@Component
public class CveArchiver {
  private static final Logger LOG = LoggerFactory.getLogger(CveArchiver.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final CVEDAO cveDao;

  public CveArchiver(CVEDAO dao) {
    cveDao = dao;
  }

  private Nvd_cve_feed_json_1_1 deserialize(String jsonStr) throws JsonProcessingException {
    return MAPPER.readValue(jsonStr, Nvd_cve_feed_json_1_1.class);
  }

  @KafkaListener(topics = NVDFetchTask.MODIFIED_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleModifiedCve(String modifiedCveUpdate) {
    LOG.debug("Received modified cve update");
    try {
      Nvd_cve_feed_json_1_1 update = deserialize(modifiedCveUpdate);
      LOG.info("Processing modified cve update with {} records", update.getCVE_Items().size());
      insertMissingCves(update.getCVE_Items());
    } catch (JsonProcessingException e) {
      LOG.error("error while processing modified cve message {}", e.getMessage());
    }
  }

  @KafkaListener(topics = NVDFetchTask.RECENT_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleRecentCve(String recentCveUpdate) {
    LOG.debug("Received recent cve update");
    try {
      Nvd_cve_feed_json_1_1 update = deserialize(recentCveUpdate);
      LOG.info("Processing recent cve update with {} records", update.getCVE_Items().size());
      insertMissingCves(update.getCVE_Items());
    } catch (JsonProcessingException e) {
      LOG.error("error while processing recent cve message {}", e.getMessage());
    }
  }

  @KafkaListener(topics = NVDFetchTask.COMPLETE_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleCompleteCve(String completeCveUpdate) {
    LOG.debug("Received complete cve update");
    try {
      Nvd_cve_feed_json_1_1 update = deserialize(completeCveUpdate);
      LOG.info("Processing recent cve update with {} records", update.getCVE_Items().size());
      insertMissingCves(update.getCVE_Items());
    } catch (JsonProcessingException e) {
      LOG.error("error while processing complete cve message {}", e.getMessage());
    }
  }

  private void insertMissingCves(List<Def_cve_item> cves) {
    cves.forEach(
        a -> {
          if (!cveDao.isCveTracked(a.getCve().getCVE_data_meta().getID().toLowerCase())) {
            var newCve = new CVE();
            var nvdCve = a.getCve();
            if (!nvdCve.getDescription().getDescription_data().isEmpty()) {
              newCve.setDescription(
                  nvdCve.getDescription().getDescription_data().get(0).getValue());
            }
            newCve.setId(nvdCve.getCVE_data_meta().getID());
            if (!nvdCve.getReferences().getReference_data().isEmpty()) {
              newCve.setReferences(nvdCve.getReferences().getReference_data().get(0).getUrl());
            }
            cveDao.save(newCve);
          }
        });
  }
}
