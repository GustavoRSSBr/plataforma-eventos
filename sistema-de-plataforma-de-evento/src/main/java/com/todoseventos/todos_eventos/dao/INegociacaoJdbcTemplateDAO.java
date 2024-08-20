package com.todoseventos.todos_eventos.dao;

public interface INegociacaoJdbcTemplateDAO {

    String comprarIngresso(int idEvento, int idPessoa, String tipoIngresso);
}
