package xyz.andrewkboyd.mitresiphon.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.andrewkboyd.mitresiphon.dto.EchoResult;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("/echo/{num}")
    public EchoResult echo(@PathVariable("num") int num) {
        return new EchoResult(num);
    }

}
