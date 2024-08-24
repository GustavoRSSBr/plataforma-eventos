package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.requestDTO.ClienteAtualizarRequestDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.ClienteResponseDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponseDTO;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.usecase.ClienteService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class ClienteController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @Operation(description = "Operação para cadastrar clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    @PostMapping("/cliente")
    public ResponseEntity<?> cadastraPessoa(
            @Parameter(description = "Dados do cliente a ser cadastrado")
            @Valid @RequestBody ClienteRequestDTO clienteRequest) {
        long startTime = System.currentTimeMillis();
        clienteService.cadastrarNovaPessoa(clienteRequest);

        LoggerUtils.logElapsedTime(LOGGER, "cadastraPessoa", startTime);
        return ResponseEntity.ok(new CustomExceptionResponseDTO(SuccessMessages.CADASTRO_CLIENTE));
    }

    @Operation(description = "Operação para buscar cliente por CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado!"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar cliente!")
    })
    @GetMapping("/pessoa/{identificador}")
    public ResponseEntity<?> procuraPessoaCpfouCnpj(
            @Parameter(description = "CFP ou CNPJ do cliente a ser buscado.")
            @Valid @PathVariable String identificador) {
        long startTime = System.currentTimeMillis();
        ClienteResponseDTO pessoa = clienteService.verificarCpfOuCnpj(identificador);
        LoggerUtils.logElapsedTime(LOGGER, "procuraPessoaCpfouCnpj", startTime);
        return ResponseEntity.ok(pessoa);
    }

    @Operation(description = "Operação para listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar clientes!")
    })
    @GetMapping("/pessoa")
    public ResponseEntity<?> listaTodasPessoas() {
        long startTime = System.currentTimeMillis();
        List<ClienteResponseDTO> response = clienteService.listarPessoas();
        LoggerUtils.logElapsedTime(LOGGER, "listaTodasPessoas", startTime);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Operação para atualizar cliente por CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar cliente!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar cliente!")
    })
    @PutMapping("/pessoa/{identificador}")
    public ResponseEntity<?> atualizaPessoaPorCpfouCnpj(
            @Parameter(description = "Atualiza os dados da pessoa.")
            @Valid @PathVariable("identificador") String identificador, @RequestBody ClienteAtualizarRequestDTO clienteRequest) {
        long startTime = System.currentTimeMillis();
        ClienteResponseDTO response = clienteService.atualizarPessoa(identificador, clienteRequest);

        LoggerUtils.logElapsedTime(LOGGER, "atualizaPessoaPorCpfouCnpj", startTime);
        return ResponseEntity.ok(new CustomExceptionResponseDTO(SuccessMessages.CLIENTE_ATUALIZADO));
    }
}
