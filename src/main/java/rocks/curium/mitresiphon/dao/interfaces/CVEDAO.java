package rocks.curium.mitresiphon.dao.interfaces;

import java.util.HashSet;
import java.util.List;
import rocks.curium.mitresiphon.dto.SearchCriteria;
import rocks.curium.mitresiphon.dto.SearchResult;
import rocks.curium.mitresiphon.entities.CVE;

public interface CVEDAO extends BaseDAO<CVE> {
  SearchResult searchForCve(SearchCriteria criteria);

  void batchSave(List<CVE> cves);

  HashSet<String> findCVEsMissingInDb(HashSet<String> ramSet);

  boolean isCveTracked(String id);
}
