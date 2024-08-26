package com.todoseventos.todos_eventos.security;

import com.todoseventos.todos_eventos.controller.AutenticacaoController;
import com.todoseventos.todos_eventos.dto.requestDTO.AuthenticationRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.AcessResponseDTO;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Realiza o login do usuário.
     *
     * @param authDto Objeto contendo as credenciais de autenticação (e-mail e senha).
     * @return Um objeto AcessDTO contendo o token JWT gerado.
     * @throws CustomException se as credenciais forem inválidas ou ocorrer um erro interno.
     */
    public AcessResponseDTO login(AuthenticationRequestDTO authDto) {
        // Cria um token de autenticação usando o e-mail e a senha fornecidos
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getSenha());

        // Autentica o token
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Gera um token JWT com base na autenticação
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Retorna o token JWT
        return new AcessResponseDTO(jwt);
    }
}
