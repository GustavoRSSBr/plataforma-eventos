package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.TipoCliente;

public interface ITipoClienteJdbcTemplateDAO {
    TipoCliente buscarPorNomeTipoPessoa(String nomeTipoPessoa);
}
