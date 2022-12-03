package rocks.curium.mitresiphon.dao.interfaces;

import rocks.curium.mitresiphon.dto.SearchResult;
import rocks.curium.mitresiphon.entities.CVE;
import rocks.curium.mitresiphon.dto.SearchCriteria;

import java.util.HashSet;
import java.util.List;

public interface CVEDAO extends BaseDAO<CVE> {
    SearchResult searchForCve(SearchCriteria criteria);
    void batchSave(List<CVE> cves);
    HashSet<String> findCVEsMissingInDb(HashSet<String> ramSet);
    boolean isCveTracked(String id);
}
