package rocks.curium.mitresiphon.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;
import rocks.curium.mitresiphon.entities.ResourceStat;

@RunWith(SpringRunner.class)
@WebMvcTest(ResourcesStatController.class)
class ResourceStatControllerTests {
  @Autowired private MockMvc mvc;

  @MockBean private ResourceStatDAO resourceStatDAO;

  @Test
  void getResourceStatInfo() throws Exception {
    ResourceStat dto = new ResourceStat();
    dto.setResource("test");
    Mockito.when(resourceStatDAO.getResourceStat(Mockito.any())).thenReturn(dto);

    mvc.perform(
            MockMvcRequestBuilders.get("/api/resource-stats/resource?url=test")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.resource").value("test"));
  }
}
