package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Participacao;

import java.util.List;

public interface IParticipacaoJdbcTemplateDAO {
    Participacao salvarParticipacao(Participacao participacao);
    Participacao localizarPorId(Integer idParticipacao);
    List<Participacao> localizarPorIdEvento(Integer idEvento);
}

