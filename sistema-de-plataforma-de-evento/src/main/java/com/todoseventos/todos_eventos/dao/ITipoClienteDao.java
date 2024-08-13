package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.TipoClienteModel;

public interface ITipoClienteDao {
    TipoClienteModel buscarPorNomeTipoPessoa(String nomeTipoPessoa);
}
