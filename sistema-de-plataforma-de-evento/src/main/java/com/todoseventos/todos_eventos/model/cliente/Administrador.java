package com.todoseventos.todos_eventos.model.cliente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Administrador {
    private Long idPessoa;
    private String email;
    private String senha;
}

