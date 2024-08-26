package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Email;

import java.util.List;

public interface IEmailJdbcTemplateDAO {

    public List<Email> localizarPorIdEvento(Integer idEvento);
}
