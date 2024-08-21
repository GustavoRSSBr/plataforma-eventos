package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaResponseDTO {
    Integer quatidadeIngressoComprada;
    Integer quatidadeIngressoDisponivel;
    Integer quantidadeMeia;
    Integer quatidadeInteira;
    Integer quantidadeVip;
    BigDecimal totalArrecadado;
}
