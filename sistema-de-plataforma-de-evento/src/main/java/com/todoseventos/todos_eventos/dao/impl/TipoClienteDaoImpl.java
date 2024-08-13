package com.todoseventos.todos_eventos.dao.impl;

<<<<<<< Updated upstream:sistema-de-plataforma-de-evento/src/main/java/com/todoseventos/todos_eventos/dao/impl/TipoClienteDaoImpl.java
import com.todoseventos.todos_eventos.dao.ITipoClienteDao;
=======
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
>>>>>>> Stashed changes:sistema-de-plataforma-de-evento/src/main/java/com/todoseventos/todos_eventos/dao/TipoClienteDao.java
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.TipoClienteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class TipoClienteDaoImpl implements ITipoClienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TipoClienteModel buscarPorNomeTipoPessoa(String nomeTipoPessoa) {
        String sql = "SELECT * FROM procurar_tipo_cliente_por_nome(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoClienteModel.class), nomeTipoPessoa);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_POR_NOME + e.getMessage());
        }
    }
}
