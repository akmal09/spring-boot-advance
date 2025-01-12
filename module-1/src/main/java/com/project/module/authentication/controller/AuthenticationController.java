package com.project.module.authentication.controller;

import com.project.module.authentication.dto.JwtTokenResponseDTO;
import com.project.module.authentication.dto.RefreshTokenRequestDto;
import com.project.module.authentication.service.RefreshTokenService;
import com.project.module.entities.RefreshToken;
import com.project.module.entities.User;
import com.project.module.authentication.dto.LoginUserDto;
import com.project.module.authentication.dto.RegisterUserDto;
import com.project.module.authentication.service.AuthenticationService;
import com.project.module.authentication.service.UserService;
import com.project.module.repository.UserRepository;
import com.project.module.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/test-api")
    public ResponseEntity<?> testApi(){
        System.out.println("lalala");
        return new ResponseEntity<>("test-connection", HttpStatus.OK);
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getListUser(){
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) throws Exception {

        return authenticationService.login(loginUserDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return refreshTokenService.refreshToken(refreshTokenRequestDto);
    }
}
