package rocks.curium.mitresiphon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("redirectController")
public class RedirectController {
  @GetMapping(path = "/")
  public void redirectToSwaggerUi(
      ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect("/swagger-ui.html");
  }
}
