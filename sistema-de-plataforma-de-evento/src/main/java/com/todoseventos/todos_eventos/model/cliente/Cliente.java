package com.todoseventos.todos_eventos.model.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {

    private Integer idPessoa;
    private String nome;
    private String cpf;
    private String cnpj;
    private String email;
    private String senha;
    private String telefone;
    private String dataNascimento;
    private Integer tipo_pessoa;
}

