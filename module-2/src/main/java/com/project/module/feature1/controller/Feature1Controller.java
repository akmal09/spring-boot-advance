package com.project.module.feature1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class Feature1Controller {
    @GetMapping("/test-api")
    public ResponseEntity<?> testApi(){
        System.out.println("lalala");
        return new ResponseEntity<>("test-connection", HttpStatus.OK);
    }
}
