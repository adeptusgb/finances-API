package com.example.finances.services;

import com.example.finances.DTO.AuthenticationRequestDTO;
import com.example.finances.DTO.AuthenticationResponseDTO;
import com.example.finances.DTO.RegisterRequestDTO;
import com.example.finances.entities.user.Role;
import com.example.finances.entities.user.User;
import com.example.finances.exceptions.DuplicateResourceException;
import com.example.finances.exceptions.InvalidCredentialsException;
import com.example.finances.exceptions.ResourceNotFoundException;
import com.example.finances.repositories.UserRepository;
import com.example.finances.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO req) {
        User user;
        try {
            user = User.builder()
                    .username(req.username())
                    .password(passwordEncoder.encode(req.password()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidCredentialsException("username taken");
        }

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponseDTO(jwtToken);
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.username(),
                            req.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("credentials invalid");
        }
        var user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponseDTO(jwtToken);
    }
}
