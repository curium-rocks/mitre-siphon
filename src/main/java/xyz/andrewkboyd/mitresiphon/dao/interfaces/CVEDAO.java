package xyz.andrewkboyd.mitresiphon.dao.interfaces;

import xyz.andrewkboyd.mitresiphon.dto.SearchCriteria;
import xyz.andrewkboyd.mitresiphon.dto.SearchResult;
import xyz.andrewkboyd.mitresiphon.entities.CVE;

import java.util.HashSet;
import java.util.List;

public interface CVEDAO extends BaseDAO<CVE> {
    SearchResult searchForCve(SearchCriteria criteria);
    void batchSave(List<CVE> cves);
    HashSet<String> findCVEsMissingInDb(HashSet<String> ramSet);
    boolean isCveTracked(String id);
}
