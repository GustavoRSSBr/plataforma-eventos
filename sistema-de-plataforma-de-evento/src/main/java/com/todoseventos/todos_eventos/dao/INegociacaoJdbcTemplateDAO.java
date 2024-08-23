package com.todoseventos.todos_eventos.dao;

/**
 * Interface para operações de negociação de ingressos utilizando JdbcTemplate.
 */
public interface INegociacaoJdbcTemplateDAO {

    /**
     * Método para comprar um ingresso.
     *
     * @param idEvento Identificador único do evento.
     * @param idPessoa Identificador único da pessoa.
     * @param tipoIngresso Tipo de ingresso para o evento.
     * @return String Resultado da compra do ingresso.
     */
    String comprarIngresso(int idEvento, int idPessoa, String tipoIngresso);
}
