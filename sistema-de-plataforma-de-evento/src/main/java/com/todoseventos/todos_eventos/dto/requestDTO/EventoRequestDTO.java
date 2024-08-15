package com.todoseventos.todos_eventos.dto.requestDTO;

import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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
