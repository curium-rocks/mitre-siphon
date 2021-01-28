package xyz.andrewkboyd.mitresiphon.dao;

import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import xyz.andrewkboyd.mitresiphon.dao.interfaces.CVEDAO;
import xyz.andrewkboyd.mitresiphon.dto.SearchCriteria;
import xyz.andrewkboyd.mitresiphon.dto.SearchResult;
import xyz.andrewkboyd.mitresiphon.entities.CVE;

import java.util.HashSet;
import java.util.List;

public class CVEDAOImpl extends BaseDaoImpl<CVE> implements CVEDAO {

    @Override
    public SearchResult searchForCve(SearchCriteria criteria) {
        return null;
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
