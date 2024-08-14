package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;

public interface IClienteJuridicaDao {
    ClienteJuridico salvarCliJuridico(ClienteJuridico pessoaJuridica);
    ClienteJuridico atualizarJuridico(ClienteJuridico pessoaJuridica);
    ClienteJuridico procurarCnpj(String cnpj);
}
