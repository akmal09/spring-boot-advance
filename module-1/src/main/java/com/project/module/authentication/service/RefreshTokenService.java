package com.project.module.authentication.service;

import com.project.module.authentication.dto.JwtTokenResponseDTO;
import com.project.module.authentication.dto.RefreshTokenRequestDto;
import com.project.module.entities.RefreshToken;
import com.project.module.entities.User;
import com.project.module.repository.RefreshTokenRepository;
import com.project.module.repository.UserRepository;
import com.project.module.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    public RefreshToken createRefreshToken(String username, String jwt){

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(600000))
                .accessToken(jwt)
                .expireDateAccessToken(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .userId(userRepository.findByUsername(username).get().getId())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto){
        Optional<RefreshToken> getRefreshToken = refreshTokenRepository.findByToken(refreshTokenRequestDto.getRefreshToken());
        if(getRefreshToken.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefreshToken refreshToken = getRefreshToken.get();
        if(!this.verifyExpiration(refreshToken)){
            throw new RuntimeException(refreshToken.getToken() + " Refresh token is expired. Please make a new login..!");
        }

        Optional<User> getUserInfo = userRepository.findById(refreshToken.getUserId());

        if(getUserInfo.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User userInfo = getUserInfo.get();

        String accessToken = jwtService.generateToken(userInfo);

        return new ResponseEntity<>(new JwtTokenResponseDTO(accessToken, refreshToken.getToken()), HttpStatus.OK);
    }

    public Boolean verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpireDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            return false;
        }
        return true;
    }
}
