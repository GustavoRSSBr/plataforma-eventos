package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.ParticipacaoModel;

import java.util.List;

public interface IParticipacaoDao {
    ParticipacaoModel salvarParticipacao(ParticipacaoModel participacao);
    ParticipacaoModel atualizarParticipacao(ParticipacaoModel participacao);
    ParticipacaoModel localizarPorId(Integer idParticipacao);
    List<ParticipacaoModel> localizarPorIdEvento(Integer idEvento);
}

