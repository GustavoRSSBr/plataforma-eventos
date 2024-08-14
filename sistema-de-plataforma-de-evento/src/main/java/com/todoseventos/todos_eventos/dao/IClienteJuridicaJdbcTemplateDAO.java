package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;

public interface IClienteJuridicaJdbcTemplateDAO {
    ClienteJuridico salvarCliJuridico(ClienteJuridico pessoaJuridica);
    ClienteJuridico atualizarJuridico(ClienteJuridico pessoaJuridica);
    ClienteJuridico procurarCnpj(String cnpj);
}
