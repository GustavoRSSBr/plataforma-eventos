package com.todoseventos.todos_eventos.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteResponseDTO {

    private Integer idPessoa;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private String cnpj;
    private TipoClienteEnum tipo_pessoa;
}
