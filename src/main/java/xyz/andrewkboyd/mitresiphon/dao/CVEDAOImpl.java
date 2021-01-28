package xyz.andrewkboyd.mitresiphon.dao;

import org.hibernate.Session;
import xyz.andrewkboyd.mitresiphon.dao.interfaces.CVEDAO;
import xyz.andrewkboyd.mitresiphon.dto.SearchCriteria;
import xyz.andrewkboyd.mitresiphon.dto.SearchResult;
import xyz.andrewkboyd.mitresiphon.entities.CVE;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CVEDAOImpl extends BaseDaoImpl<CVE> implements CVEDAO {

    @Override
    public SearchResult searchForCve(SearchCriteria criteria) {
        Session session = getSession();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<criteria.getTerms().size(); i++) {
            sb.append(":param")
                .append(i);
            if(i + 1 < criteria.getTerms().size()) {
                sb.append(" | ");
            }
        }
        var query =
                session.createQuery(String.format("from cve where to_tsvector(cve.description) @@ to_tsquery('%s') order by id offset :offset limit :limit",sb.toString()), CVE.class);

        var countQuery =
                session.createQuery(String.format("select count(*) from cve where to_tsvector(cve.description) @@ to_tsquery('%s') offset :offset limit :limit",sb.toString()), Long.class);


        for(int i = 0; i<criteria.getTerms().size(); i++) {
            query.setParameter(String.format("param%s", i),criteria.getTerms().get(i));
            query.setParameter(String.format("param%s", i),criteria.getTerms().get(i));
        }
        var result = new SearchResult();
        result.setTotalMatches(countQuery.uniqueResult());
        result.setMatches(query.stream().collect(Collectors.toList()));
        result.setOffset(criteria.getOffset());
        result.setCount(criteria.getCount());
        return result;
    }

    @Override
    public void batchSave(List<CVE> cves) {

    }

    @Override
    public boolean isCveTracked(String cveId){
        Session session = getSession();
        return session.find(CVE.class, cveId) != null;
    }

    //TODO: add query to find ids present in hashset that aren't present in DB
    @Override
    public HashSet<String> findCVEsMissingInDb(HashSet<String> ramSet) {
        return null;
    }
}
