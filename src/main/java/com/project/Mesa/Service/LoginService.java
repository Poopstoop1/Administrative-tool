package com.project.Mesa.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.Mesa.Controller.dto.auth.LoginRequestDTO;
import com.project.Mesa.Controller.dto.auth.LoginResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.username(),
                dto.password()
            )
        );

        String username = authentication.getName();
        
        String token = tokenService.generateToken(username);

        return new LoginResponseDTO(token);
    }
}
