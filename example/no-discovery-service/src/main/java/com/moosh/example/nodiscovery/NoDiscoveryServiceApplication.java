package com.moosh.example.nodiscovery;

import com.moosh.registry.client.EnableClientRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableClientRegistry
@SpringBootApplication
public class NoDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoDiscoveryServiceApplication.class, args);
    }
}
