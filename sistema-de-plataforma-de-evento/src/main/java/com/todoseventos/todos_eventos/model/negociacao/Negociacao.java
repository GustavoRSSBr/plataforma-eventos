package com.todoseventos.todos_eventos.model.negociacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Negociacao {

    private Integer idEvento;
    private Integer idPessoa;
}
