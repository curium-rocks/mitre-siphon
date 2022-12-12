package rocks.curium.mitresiphon.dao;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.dto.SearchCriteria;
import rocks.curium.mitresiphon.dto.SearchResult;
import rocks.curium.mitresiphon.entities.CVE;

public class CVEDAOImpl extends BaseDaoImpl<CVE> implements CVEDAO {

  @Override
  public SearchResult searchForCve(SearchCriteria criteria) {
    Session session = getSession();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < criteria.getTerms().size(); i++) {
      sb.append(":param").append(i);
      if (i + 1 < criteria.getTerms().size()) {
        sb.append(" | ");
      }
    }
    var query =
        session.createNativeQuery(
            String.format(
                "select * from cve where to_tsvector(cve.description) @@ to_tsquery(%s) order by id offset :offset limit :limit",
                sb.toString()),
            CVE.class);

    var countQuery =
        session.createNativeQuery(
            String.format(
                "select count(*) from cve where to_tsvector(cve.description) @@ to_tsquery(%s)",
                sb.toString()));

    for (int i = 0; i < criteria.getTerms().size(); i++) {
      query.setParameter(String.format("param%s", i), criteria.getTerms().get(i));
      countQuery.setParameter(String.format("param%s", i), criteria.getTerms().get(i));
    }

    query.setParameter("limit", criteria.getCount());
    query.setParameter("offset", criteria.getOffset());

    var result = new SearchResult();
    result.setTotalMatches(BigInteger.valueOf((Long) countQuery.uniqueResult()));
    result.setMatches(query.stream().collect(Collectors.toList()));
    result.setOffset(criteria.getOffset());
    result.setCount(criteria.getCount());
    return result;
  }

  @Override
  public void batchSave(List<CVE> cves) {
    // TODO: add support for batch save
  }

  @Override
  public boolean isCveTracked(String cveId) {
    Session session = getSession();
    return session.find(CVE.class, cveId) != null;
  }

  // TODO: add query to find ids present in hashset that aren't present in DB
  @Override
  public HashSet<String> findCVEsMissingInDb(HashSet<String> ramSet) {
    return null;
  }
}
