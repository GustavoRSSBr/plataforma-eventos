package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.EventoModel;

import java.util.List;
import java.util.Optional;

public interface IEventoDao {
    EventoModel salvarEvento(EventoModel evento);
    EventoModel atualizarEvento(EventoModel evento);
    Optional<EventoModel> procurarPorNome(String nomeEvento);
    Optional<EventoModel> procurarPorId(Integer idEvento);
    List<EventoModel> localizarEvento();
    void deletarPorId(Integer idEvento);
}

