package rocks.curium.mitresiphon.actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import rocks.curium.mitresiphon.dao.CVEDAOImpl;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.generated.models.*;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
class CveArchiverTests {

    private String serialize(Nvd_cve_feed_json_1_1 feed) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(feed);
    }

    private Nvd_cve_feed_json_1_1 getFeed(){
        Nvd_cve_feed_json_1_1 feed = new Nvd_cve_feed_json_1_1();
        var items = new ArrayList<Def_cve_item>();
        feed.setCVE_Items(items);
        var newDef = new Def_cve_item();
        items.add(newDef);
        var json = new CVE_JSON_4_0_min_1_1();
        newDef.setCve(json);
        var meta = new CVE_data_meta();
        meta.setID("TEST");
        json.setCVE_data_meta(meta);
        var desc = new Description();
        var lstring = new ArrayList<Lang_string>();
        desc.setDescription_data(lstring);
        json.setDescription(desc);

        var ref = new References();
        ref.setReference_data(new ArrayList<>());
        json.setReferences(ref);
        return feed;
    }

    @Test
    void canProcessCompleteMessage() throws JsonProcessingException {
        CVEDAO cveDae = Mockito.mock(CVEDAOImpl.class);
        var feed = getFeed();
        CveArchiver archiver = new CveArchiver(cveDae);
        archiver.handleCompleteCve(serialize(feed));
        Mockito.verify(cveDae).save(Mockito.any());
    }

    @Test
    void canProcessModifiedMessage() throws JsonProcessingException {
        CVEDAO cveDae = Mockito.mock(CVEDAOImpl.class);
        CveArchiver archiver = new CveArchiver(cveDae);
        Nvd_cve_feed_json_1_1 feed = getFeed();
        archiver.handleModifiedCve(serialize(feed));
        Mockito.verify(cveDae, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void canProcessRecentMessage() throws JsonProcessingException {
        CVEDAO cveDae = Mockito.mock(CVEDAOImpl.class);
        CveArchiver archiver = new CveArchiver(cveDae);
        Nvd_cve_feed_json_1_1 feed = getFeed();
        archiver.handleRecentCve(serialize(feed));
        Mockito.verify(cveDae, Mockito.times(1)).save(Mockito.any());
    }
}
