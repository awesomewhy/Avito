package com.example.avito.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Person {
    @GetMapping("/hello")
    public String getHello() {
        return "hello";//a
    }
}
