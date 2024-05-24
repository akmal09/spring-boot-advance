package com.project.module.authentication.controller;

import com.project.entities.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/module1")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test-api")
    public ResponseEntity<?> testApi(){
        return new ResponseEntity<>("test-connection", HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> testaDD(){
        User user = new User();
        user.setCreatedAt(new Date());
        user.setEmail("tes12@mail.com");
        user.setPassword("1234");
        user.setFullName("tes lala");
        User saveUser = userRepository.save(user);


        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getListUser(){
        List<User> list = userRepository.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
