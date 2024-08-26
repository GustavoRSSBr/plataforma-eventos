package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.carteira.Carteira;

public interface ICarteiraJdbcTemplateDAO {
    Double depositar(int idPessoa, double valor);

    void criarCarteira(Carteira carteira);

    Double consultarSaldo(int idPessoa);
}
