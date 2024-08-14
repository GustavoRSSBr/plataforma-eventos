package com.todoseventos.todos_eventos.controller;


import com.todoseventos.todos_eventos.dto.requestDTO.AuthenticationDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.JwtResponse;
import com.todoseventos.todos_eventos.security.jwt.JwtUtils;
import com.todoseventos.todos_eventos.model.cliente.UserDetailsImpl;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AutenticacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Operation(summary = "Faz login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Dados para fazer login")
            @Valid @RequestBody AuthenticationDTO authDto) {
        long startTime = System.currentTimeMillis();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LoggerUtils.logElapsedTime(LOGGER, "login", startTime);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
