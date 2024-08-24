package com.todoseventos.todos_eventos.model.carteira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Carteira {

    private Integer idCarteira;
    private Double saldo;
    private Integer idPessoa;
}
