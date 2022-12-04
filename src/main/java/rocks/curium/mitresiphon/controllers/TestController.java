package rocks.curium.mitresiphon.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.curium.mitresiphon.dto.EchoResult;

/** Simple test controller for test stubs */
@RestController
@RequestMapping("api/test")
public class TestController {

  /**
   * Echo a received number back to a client in JSON
   *
   * @param num {int} number input
   * @return response wrapping received number
   */
  @GetMapping("/echo/{num}")
  public EchoResult echo(@PathVariable("num") int num) {
    return new EchoResult(num);
  }
}
