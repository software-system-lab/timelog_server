package csie.ntut.edu.tw.timelog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheck {
    @GetMapping(value = "/")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Alive");
    }
}
