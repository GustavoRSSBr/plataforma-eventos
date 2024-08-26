package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteFisicoResponseDTO {

        private Integer idPessoa;
        private String nome;
        private String cpf;
        private String email;
        private String senha;
        private String telefone;
        private String dataNascimento;
        private Integer tipo_pessoa;
}
