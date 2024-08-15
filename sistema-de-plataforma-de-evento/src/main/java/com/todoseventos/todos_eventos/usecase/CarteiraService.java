package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.ICarteiraJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.DepositoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.DepositoResponseDTO;
import com.todoseventos.todos_eventos.model.carteira.CarteiraModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarteiraService {

    @Autowired
    private ICarteiraJdbcTemplateDAO iCarteiraJdbcTemplateDAO;

    @Transactional
    public DepositoResponseDTO depositar(DepositoRequestDTO request){
        iCarteiraJdbcTemplateDAO.depositar(request.getIdPessoa(), request.getValor());

        CarteiraModel carteiraAtualizada = iCarteiraJdbcTemplateDAO.buscarCarteiraPorIdPessoa(request.getIdPessoa());

        return new DepositoResponseDTO("Depósito realizado com sucesso!", null);
    }

    public void criarCarteiraParaNovoCliente(int idPessoa){
        CarteiraModel novaCarteira = CarteiraModel.builder().idPessoa(idPessoa).saldo(0.0).build();
        iCarteiraJdbcTemplateDAO.criarCarteira(novaCarteira);
    }
}
