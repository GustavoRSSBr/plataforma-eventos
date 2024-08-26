package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.dto.responseDTO.EstatisticaResponseDTO;
import com.todoseventos.todos_eventos.model.evento.Evento;

import java.util.List;
import java.util.Optional;

public interface IEventoJdbcTemplateDAO {
    Evento salvarEvento(Evento evento);
    Evento atualizarEvento(Evento evento);
    Optional<Evento> procurarPorNome(String nomeEvento);
    Optional<Evento> procurarPorId(Integer idEvento);
    List<Evento> listarEvento();
    Evento encerrarEvento(Integer idEvento);
    EstatisticaResponseDTO coletarEstatistica(Integer idEvento);
}

