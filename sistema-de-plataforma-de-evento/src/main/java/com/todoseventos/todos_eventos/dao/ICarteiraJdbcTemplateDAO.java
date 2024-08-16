package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.carteira.CarteiraModel;

public interface ICarteiraJdbcTemplateDAO {
    Double depositar(int idPessoa, double valor);

    void criarCarteira(CarteiraModel carteira);

    Double consultarSaldo(int idPessoa);
}
