package com.example.avito;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
public class AvitoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvitoApplication.class, args);
    }
}
