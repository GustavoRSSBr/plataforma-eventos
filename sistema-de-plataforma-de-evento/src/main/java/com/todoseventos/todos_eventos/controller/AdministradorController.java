package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponseDTO;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.model.cliente.Administrador;
import com.todoseventos.todos_eventos.usecase.AdministradorService;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class AdministradorController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdministradorController.class);

    @Autowired
    private AdministradorService administradorService;

    @Operation(description = "Operação para cadastrar administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro de administrador realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro do administrador!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro do administrador!")
    })
    @PostMapping("/admin")
    public ResponseEntity<?> cadastraAdministrador(
            @Parameter(description = "Dados do administrador a ser cadastrado")
            @Valid @RequestBody Administrador administradorRequest) {
        long startTime = System.currentTimeMillis();
        administradorService.salvarAdministrador(administradorRequest);
        LoggerUtils.logElapsedTime(LOGGER, "cadastraAdministrador", startTime);
        return ResponseEntity.ok(new CustomExceptionResponseDTO(SuccessMessages.CADASTRO_ADMINISTRADOR));
    }
}
