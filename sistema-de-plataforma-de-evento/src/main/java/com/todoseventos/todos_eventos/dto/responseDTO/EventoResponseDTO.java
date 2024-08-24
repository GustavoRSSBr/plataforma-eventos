package com.todoseventos.todos_eventos.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoResponseDTO {
    private Integer idEvento;
    private String nome_evento;
    private String dataHora_evento;
    private String dataHora_eventofinal;
    private String descricao;
    private String status;
    private CategoriaEnum categoria;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    private BigDecimal valorIngresso;
    private Integer limitePessoas;
}
