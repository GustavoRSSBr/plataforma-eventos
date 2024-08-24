package com.todoseventos.todos_eventos.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAtualizarRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
}
