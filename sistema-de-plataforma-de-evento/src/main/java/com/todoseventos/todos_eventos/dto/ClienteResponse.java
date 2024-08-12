package com.todoseventos.todos_eventos.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse {
    private Integer idPessoa;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private String cnpj;
    private TipoClienteEnum tipo_pessoa;
}
