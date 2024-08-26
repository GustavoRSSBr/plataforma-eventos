package com.todoseventos.todos_eventos.dto.requestDTO;

import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {

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
