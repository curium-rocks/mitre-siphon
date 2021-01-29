package xyz.andrewkboyd.mitresiphon.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.andrewkboyd.mitresiphon.dao.interfaces.CVEDAO;
import xyz.andrewkboyd.mitresiphon.dto.SearchResult;

import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CVEController.class)
class CVEControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CVEDAO cvedao;

    @Test
    @WithMockUser(username = "test", roles={"user"})
    void executeTestSearch() throws Exception
    {
        SearchResult result = new SearchResult();
        result.setCount(1);
        result.setTotalMatches(BigInteger.valueOf(1));
        result.setOffset(BigInteger.valueOf(0));
        result.setCount(10);

        Mockito.when(cvedao.searchForCve(Mockito.any())).thenReturn(result);

        mvc.perform( MockMvcRequestBuilders
                .get("/api/cve/search?offset=0&count=10&terms=\"test\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalMatches").value("1"));

    }
}