package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IEventoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.responseDTO.EstatisticaResponseDTO;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisico;
import com.todoseventos.todos_eventos.model.evento.Evento;
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
import java.util.Optional;

@Repository
class EventoJdbcTemplateDAOImpl implements IEventoJdbcTemplateDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventoJdbcTemplateDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Evento salvarEvento(Evento evento) {
        String sql = "SELECT inserir_evento(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            setPreparedStatementParameters(ps, evento);
            ps.execute();
            return null;
        });
        Integer idEvento = jdbcTemplate.queryForObject("SELECT currval(pg_get_serial_sequence('evento','id_evento'))", Integer.class);
        evento.setIdEvento(idEvento);
        return evento;
    }

    @Override
    @Transactional
    public Evento atualizarEvento(Evento evento) {
        String sql = "SELECT atualizar_evento(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, evento.getIdEvento());
            ps.setString(2, evento.getNome_evento().trim());
            ps.setString(3, evento.getDataHora_evento());
            ps.setString(4, evento.getDataHora_eventofinal());
            ps.setString(5, evento.getDescricao().trim());
            ps.setString(6, String.valueOf(evento.getStatus()));
            ps.setInt(7, evento.getId_categoria());
            ps.setBigDecimal(8, evento.getValorIngresso());
            ps.setInt(9, evento.getLimitePessoas());
            ps.execute();
            return null;
        });
        return evento;
    }

    @Override
    @Transactional
    public Optional<Evento> procurarPorNome(String nomeEvento) {
        String sql = "SELECT * FROM procurar_evento_por_nome(?)";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Evento.class), nomeEvento.trim()));

    }

    @Override
    @Transactional
    public Optional<Evento> procurarPorId(Integer idEvento) {
        String sql = "SELECT * FROM procurar_evento_por_id(?)";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Evento.class), idEvento));

    }

    @Override
    @Transactional
    public List<Evento> listarEvento() {
        String sql = "SELECT * FROM localizar_evento()";
        logger.info("Executando SQL para buscar evento: {}", sql);

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Evento.class));

    }

    @Override
    @Transactional
    public void deletarPorId(Integer idEvento) {
        String sql = "SELECT deletar_evento(?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, idEvento);
            ps.execute();
            return null;
        });

    }

    @Override
    @Transactional
    public Evento encerrarEvento(Integer idEvento) {
        String sql = "SELECT cancelar_evento(?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, idEvento);
            ps.execute();
            return null;
        });
        return null;
    }

    @Override
    public EstatisticaResponseDTO coletarEstatistica(Integer idEvento) {
        String sql = "SELECT * FROM coletarEstatisticaEvento(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(EstatisticaResponseDTO.class), idEvento);
    }


    private void setPreparedStatementParameters(PreparedStatement ps, Evento evento) throws SQLException {
        ps.setString(1, evento.getNome_evento().trim());
        ps.setString(2, evento.getDataHora_evento());
        ps.setString(3, evento.getDataHora_eventofinal());
        ps.setString(4, evento.getDescricao().trim());
        ps.setString(5, String.valueOf(evento.getStatus()));
        ps.setInt(6, evento.getId_categoria());
        ps.setBigDecimal(7, evento.getValorIngresso());
        ps.setInt(8, evento.getLimitePessoas());
    }
}
