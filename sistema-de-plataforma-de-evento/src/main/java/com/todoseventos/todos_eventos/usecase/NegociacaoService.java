package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.INegociacaoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.responseDTO.NegociacaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NegociacaoService {

    @Autowired
    INegociacaoJdbcTemplateDAO iNegociacaoJdbcTemplateDAO;

    @Transactional
    public NegociacaoResponseDTO comprarIngresso(Integer idEvento, Integer idPessoa, String tipoIngresso) {
        String resultado = iNegociacaoJdbcTemplateDAO.comprarIngresso(idEvento, idPessoa, tipoIngresso);
            return new NegociacaoResponseDTO(resultado);
    }
}
