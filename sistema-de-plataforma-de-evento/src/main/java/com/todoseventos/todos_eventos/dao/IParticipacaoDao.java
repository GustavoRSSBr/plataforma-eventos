package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Participacao;

import java.util.List;

public interface IParticipacaoDao {
    Participacao salvarParticipacao(Participacao participacao);
    Participacao atualizarParticipacao(Participacao participacao);
    Participacao localizarPorId(Integer idParticipacao);
    List<Participacao> localizarPorIdEvento(Integer idEvento);
}

