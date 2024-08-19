package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.ICarteiraJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.DepositoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.DepositoResponseDTO;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.model.carteira.CarteiraModel;
import com.todoseventos.todos_eventos.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarteiraService {

    @Autowired
    private ICarteiraJdbcTemplateDAO iCarteiraJdbcTemplateDAO;

    @Autowired
    private Validador validador;

    @Transactional
    public DepositoResponseDTO depositar(DepositoRequestDTO request){
        validador.validar(request);
        Double saldoAtualizado = iCarteiraJdbcTemplateDAO.depositar(request.getIdPessoa(), request.getValor());
        return new DepositoResponseDTO(SuccessMessages.DEPOSITO_REALIZADO, saldoAtualizado);
    }

    public void criarCarteiraParaNovoCliente(int idPessoa){
        CarteiraModel novaCarteira = CarteiraModel.builder().idPessoa(idPessoa).saldo(0.0).build();
        iCarteiraJdbcTemplateDAO.criarCarteira(novaCarteira);
    }

    @Transactional(readOnly = true)
    public Double consultarSaldo(int idPessoa) {
        return iCarteiraJdbcTemplateDAO.consultarSaldo(idPessoa);
    }
}
