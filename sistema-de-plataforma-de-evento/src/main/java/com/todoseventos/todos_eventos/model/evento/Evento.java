package com.todoseventos.todos_eventos.model.evento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Evento implements Serializable {

    private Integer idEvento;
    private String nome_evento;
    private String dataHora_evento;
    private String dataHora_eventofinal;
    private String descricao;
    private String status;
    private Integer id_categoria;
    private Integer id_endereco;
}
