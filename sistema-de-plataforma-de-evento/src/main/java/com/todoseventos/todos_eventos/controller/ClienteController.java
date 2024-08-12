package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.CustomExceptionResponse;
import com.todoseventos.todos_eventos.dto.ClienteRequest;
import com.todoseventos.todos_eventos.dto.ClienteResponse;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.usecase.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> postPessoa(@RequestBody ClienteRequest clienteRequest) {
        ClienteResponse response = clienteService.cadastrarNovaPessoa(clienteRequest);
        return ResponseEntity.ok(new CustomExceptionResponse(CustomException.CADASTRO_CLIENTE));
    }

    @Operation(description = "Operação para buscar cliente por CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado!"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar cliente!")
    })
    @GetMapping("/pessoa/{identificador}")
    public ResponseEntity<?> getPessoa(@PathVariable String identificador) {
        ClienteResponse pessoa = clienteService.verificarCpfOuCnpj(identificador);
        return ResponseEntity.ok(pessoa);
    }



    @Operation(description = "Operação para listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar clientes!")
    })
    @GetMapping("/pessoa")
    public ResponseEntity<?> getPessoa() {
        List<ClienteResponse> response = clienteService.listarPessoas();
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Operação para atualizar cliente por CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar cliente!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar cliente!")
    })
    @PutMapping("/pessoa/{identificador}")
    public ResponseEntity<?> putPessoa(@PathVariable("identificador") String identificador, @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse response = clienteService.atualizarPessoa(identificador, clienteRequest);
        return ResponseEntity.ok(new CustomExceptionResponse(CustomException.CLIENTE_ATUALIZADO));
    }
}
