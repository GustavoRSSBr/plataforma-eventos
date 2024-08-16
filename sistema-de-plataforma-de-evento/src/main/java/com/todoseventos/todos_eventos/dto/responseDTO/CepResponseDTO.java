package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CepResponseDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
}
