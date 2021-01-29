package xyz.andrewkboyd.mitresiphon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("redirectController")
public class RedirectController {
    @RequestMapping("/")
    public void redirectToSwaggerUi(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
