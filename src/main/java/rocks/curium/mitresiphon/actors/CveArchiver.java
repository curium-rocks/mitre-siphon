package rocks.curium.mitresiphon.actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.entities.CVE;
import rocks.curium.mitresiphon.generated.models.Def_cve_item;
import rocks.curium.mitresiphon.tasks.NVDFetchTask;

@Component
public class CveArchiver {
  private static final Logger LOG = LoggerFactory.getLogger(CveArchiver.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final CVEDAO cveDao;

  public CveArchiver(CVEDAO dao) {
    cveDao = dao;
  }

  private Def_cve_item deserialize(String jsonStr) throws JsonProcessingException {
    return MAPPER.readValue(jsonStr, Def_cve_item.class);
  }

  @KafkaListener(topics = NVDFetchTask.MODIFIED_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleModifiedCve(String modifiedCveUpdate) {
    LOG.debug("Received modified cve update");
    try {
      final Def_cve_item update = deserialize(modifiedCveUpdate);
      LOG.debug("Processing modified cve update");
      track(update);
    } catch (JsonProcessingException e) {
      LOG.error("error while processing modified cve message {}", e.getMessage());
    }
  }

  @KafkaListener(topics = NVDFetchTask.RECENT_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleRecentCve(String recentCveUpdate) {
    LOG.debug("Received recent cve update");
    try {
      final Def_cve_item update = deserialize(recentCveUpdate);
      LOG.debug("Processing recent cve update");
      track(update);
    } catch (JsonProcessingException e) {
      LOG.error("error while processing recent cve message {}", e.getMessage());
    }
  }

  @KafkaListener(topics = NVDFetchTask.COMPLETE_KAFKA_TOPIC, groupId = "cve-pg-archiver-group")
  public void handleCompleteCve(String completeCveUpdate) {
    LOG.debug("Received complete cve update");
    try {
      Def_cve_item update = deserialize(completeCveUpdate);
      LOG.debug("Processing complete cve update");
      track(update);
    } catch (JsonProcessingException e) {
      LOG.error("error while processing complete cve message {}", e.getMessage());
    }
  }

  private void track(final Def_cve_item a) {
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
  }
}
