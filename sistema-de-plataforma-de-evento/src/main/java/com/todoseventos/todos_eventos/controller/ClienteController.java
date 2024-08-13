package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponse;
import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.ClienteResponse;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.usecase.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class ClienteController {
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
            @Valid @RequestBody ClienteRequest clienteRequest) {
        long startTime = System.currentTimeMillis();
        ClienteResponse response = clienteService.cadastrarNovaPessoa(clienteRequest);

        logElapsedTime("cadastraPessoa", startTime);
        return ResponseEntity.ok(new CustomExceptionResponse(SuccessMessages.CADASTRO_CLIENTE));
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
        ClienteResponse pessoa = clienteService.verificarCpfOuCnpj(identificador);
        logElapsedTime("procuraPessoaCpfouCnpj", startTime);
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
        List<ClienteResponse> response = clienteService.listarPessoas();
        logElapsedTime("listaTodasPessoas", startTime);
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
            @Valid @PathVariable("identificador") String identificador, @RequestBody ClienteRequest clienteRequest) {
        long startTime = System.currentTimeMillis();
        ClienteResponse response = clienteService.atualizarPessoa(identificador, clienteRequest);

        logElapsedTime("atualizaPessoaPorCpfouCnpj", startTime);
        return ResponseEntity.ok(new CustomExceptionResponse(SuccessMessages.CLIENTE_ATUALIZADO));
    }

    /**
     * Método que registra o tempo decorrido de um método específico.
     *
     * @param methodName nome cujo o tempo de eecução está sendo medido.
     * @param startTime Tempo de início da execução do método.
     **/
    private void logElapsedTime(String methodName, long startTime) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("Método: {}, Tempo decorrido: {} ms", methodName, elapsedTime);
    }
}
