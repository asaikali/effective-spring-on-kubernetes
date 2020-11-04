package com.example.fatjardockerfile;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

  @GetMapping("/")
  String get() {
    return "Hello the time is " + LocalDateTime.now();
  }
}
