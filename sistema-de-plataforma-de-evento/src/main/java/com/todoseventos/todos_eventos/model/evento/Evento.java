package com.todoseventos.todos_eventos.model.evento;

import com.todoseventos.todos_eventos.enuns.StatusEventoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Evento implements Serializable {

    private Integer idEvento;
    private String nome_evento;
    private String dataHora_evento;
    private String dataHora_eventofinal;
    private String descricao;
    private StatusEventoEnum status;
    private Integer id_categoria;
    private Integer id_endereco;
    private BigDecimal valorIngresso;
    private Integer limitePessoas;
}