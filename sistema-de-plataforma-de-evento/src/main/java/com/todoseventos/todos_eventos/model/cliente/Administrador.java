package com.todoseventos.todos_eventos.model.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Administrador {
    private Long idPessoa;
    private String email;
    private String senha;
}

