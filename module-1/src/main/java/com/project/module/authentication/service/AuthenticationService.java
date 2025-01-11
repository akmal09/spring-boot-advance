package com.project.module.authentication.service;

import com.project.module.authentication.dto.LoginResponse;
import com.project.module.base.ResponseObject;
import com.project.module.entities.User;
import com.project.module.authentication.dto.LoginUserDto;
import com.project.module.authentication.dto.RegisterUserDto;
import com.project.module.repository.UserRepository;
import com.project.module.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public ResponseEntity<?> login(LoginUserDto loginUserDto) {
        try{

            Optional<User> findUser = userRepository.findByEmail(loginUserDto.getEmail());

            if (findUser.isEmpty() || !passwordEncoder.matches(loginUserDto.getPassword(), findUser.get().getPassword())) {
                ResponseObject responseObject = new ResponseObject();
                responseObject.setSuccess(false);
                responseObject.setCode(HttpStatus.BAD_REQUEST.value());
                responseObject.setMessage("User Not Found");
                responseObject.setData(null);
                return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
            }

            User userInformation = findUser.get();

            String jwtToken = jwtService.generateToken(userInformation);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        }catch (Exception e){
            throw e;
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    private boolean validatePassword(String password, String confirmPassword) {
        return passwordEncoder.matches(password, confirmPassword);
    }

}
