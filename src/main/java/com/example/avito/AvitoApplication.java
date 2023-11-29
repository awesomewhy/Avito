package com.example.avito;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@CrossOrigin(origins = "http://0.0.0.0:8080")
public class AvitoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvitoApplication.class, args);
    }
}
