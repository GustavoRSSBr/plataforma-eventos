package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Evento;

import java.util.List;
import java.util.Optional;

public interface IEventoDao {
    Evento salvarEvento(Evento evento);
    Evento atualizarEvento(Evento evento);
    Optional<Evento> procurarPorNome(String nomeEvento);
    Optional<Evento> procurarPorId(Integer idEvento);
    List<Evento> localizarEvento();
    void deletarPorId(Integer idEvento);
}

