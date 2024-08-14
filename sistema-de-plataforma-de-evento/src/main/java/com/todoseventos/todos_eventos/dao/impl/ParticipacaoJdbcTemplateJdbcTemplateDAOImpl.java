package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IParticipacaoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.evento.Participacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
class ParticipacaoJdbcTemplateJdbcTemplateDAOImpl implements IParticipacaoJdbcTemplateDAO {

    private static final Logger logger = LoggerFactory.getLogger(ParticipacaoJdbcTemplateJdbcTemplateDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Participacao salvarParticipacao(Participacao participacao) {
        String sql = "SELECT inserir_participacao(?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            setPreparedStatementParameters(ps, participacao);
            ps.execute();
            return null;
        });
        Integer idParticipacao = jdbcTemplate.queryForObject("SELECT currval(pg_get_serial_sequence('participacao','id_participacao'))", Integer.class);
        participacao.setIdParticipacao(idParticipacao);
        logger.info("Participação salva com ID: {}", idParticipacao);
        return participacao;

    }

    @Override
    @Transactional
    public Participacao localizarPorId(Integer idParticipacao) {
        String sql = "SELECT * FROM procurar_participacao_por_id(?)";
        logger.info("Executando SQL para buscar participação por ID: {}", sql);

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Participacao.class), idParticipacao);

    }

    @Override
    @Transactional
    public List<Participacao> localizarPorIdEvento(Integer idEvento) {
        String sql = "SELECT * FROM procurar_participacoes_por_id_evento(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Participacao.class), idEvento);

    }

    private void setPreparedStatementParameters(PreparedStatement ps, Participacao participacao) throws SQLException {
        ps.setString(1, participacao.getCpf());
        ps.setString(2, participacao.getCnpj());
        ps.setInt(3, participacao.getIdEvento());
        ps.setString(4, participacao.getStatus());
    }
}
