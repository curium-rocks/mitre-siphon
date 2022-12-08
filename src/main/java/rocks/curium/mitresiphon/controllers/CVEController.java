package rocks.curium.mitresiphon.controllers;

import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.dto.SearchCriteria;
import rocks.curium.mitresiphon.dto.SearchResult;

/** Provides indexed search capability on live updated CVE list from NVD */
@RestController
@RequestMapping("api/cve")
public class CVEController {

  final CVEDAO cveDao;

  public CVEController(CVEDAO cvedao) {
    this.cveDao = cvedao;
  }

  @GetMapping(value = "/search")
  public SearchResult searchCVEList(
      @RequestParam @Size(min = 0) BigInteger offset,
      @RequestParam @Size(min = 0, max = 100) long count,
      @RequestParam @Size(min = 1, max = 10) List<String> terms) {
    SearchCriteria criteria = new SearchCriteria();
    criteria.setTerms(terms);
    criteria.setOffset(offset);
    criteria.setCount(count);
    return cveDao.searchForCve(criteria);
  }
}
