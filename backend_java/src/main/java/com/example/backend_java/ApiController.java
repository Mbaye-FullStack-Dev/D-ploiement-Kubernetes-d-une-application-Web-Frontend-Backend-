package com.example.backend_java;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message",
                "Bonjour Kubernetes"
        );
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
                "status",
                "UP"
        );
    }

}