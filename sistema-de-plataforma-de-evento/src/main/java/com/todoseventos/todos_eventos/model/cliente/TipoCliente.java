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
public class TipoCliente implements Serializable {

    private Integer idTipoPessoa;
    private String nomeTipoPessoa;
}
