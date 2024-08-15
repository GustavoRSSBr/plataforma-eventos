package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.carteira.CarteiraModel;

public interface ICarteiraJdbcTemplateDAO {
    void depositar(int idPessoa, double valor);

    void criarCarteira(CarteiraModel carteira);

    CarteiraModel buscarCarteiraPorIdPessoa(int idPessoa);
}
