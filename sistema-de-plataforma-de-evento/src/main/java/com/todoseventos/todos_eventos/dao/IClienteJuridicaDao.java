package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.ClienteJuridicaModel;

public interface IClienteJuridicaDao {
    ClienteJuridicaModel salvarCliJuridico(ClienteJuridicaModel pessoaJuridica);
    ClienteJuridicaModel atualizarJuridico(ClienteJuridicaModel pessoaJuridica);
    ClienteJuridicaModel procurarCnpj(String cnpj);
}
