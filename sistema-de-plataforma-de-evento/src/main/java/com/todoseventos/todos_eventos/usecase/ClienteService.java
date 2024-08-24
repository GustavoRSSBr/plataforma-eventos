package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.IClienteFisicaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IClienteJuridicaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.ITipoClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.ClienteAtualizarRequestDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.ClienteResponseDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisico;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;
import com.todoseventos.todos_eventos.model.cliente.TipoCliente;
import com.todoseventos.todos_eventos.validador.Validacoes;
import com.todoseventos.todos_eventos.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {

    @Autowired
    private Validacoes validacoes;

    @Autowired
    private IClienteJdbcTemplateDAO iClienteJdbcTemplateDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private ITipoClienteJdbcTemplateDAO iTipoClienteJdbcTemplateDAO;

    @Autowired
    private IClienteFisicaJdbcTemplateDAO iClienteFisicaJdbcTemplateDAO;

    @Autowired
    private IClienteJuridicaJdbcTemplateDAO iClienteJuridicaJdbcTemplateDAO;

    @Autowired
    private Validador validador;

    /**
     * Cadastra uma nova pessoa (física ou jurídica).
     *
     * @param clienteRequest Objeto contendo os detalhes da pessoa a ser cadastrada.
     * @return Um objeto de resposta contendo os detalhes da pessoa cadastrada.
     */
    public ClienteResponseDTO cadastrarNovaPessoa(ClienteRequestDTO clienteRequest) {

        validador.validar(clienteRequest);

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            Cliente pessoaExistente = iClienteJdbcTemplateDAO.procurarPorCpf(clienteRequest.getCpf());
            if (pessoaExistente != null) {
                throw new CustomException(ExceptionMessages.CPF_JA_CADASTRADO);
            }
        } else if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            Cliente pessoaExistente = iClienteJdbcTemplateDAO.procurarPorCnpj(clienteRequest.getCnpj());
            if (pessoaExistente != null) {
                throw new CustomException(ExceptionMessages.CNPJ_JA_CADASTRADO);

            }
        }

        validador.validar(clienteRequest);

        TipoCliente tipoCliente = iTipoClienteJdbcTemplateDAO.buscarPorNomeTipoPessoa(clienteRequest.getTipo_pessoa().name());

        String encodedPassword = passwordEncoder.encode(clienteRequest.getSenha());

        Cliente pessoa = Cliente.builder()
                .nome(clienteRequest.getNome())
                .email(clienteRequest.getEmail())
                .senha(encodedPassword)
                .telefone(clienteRequest.getTelefone())
                .tipo_pessoa(tipoCliente.getIdTipoPessoa())
                .build();

        Cliente pessoaSalva = iClienteJdbcTemplateDAO.salvarCliente(pessoa);

        carteiraService.criarCarteiraParaNovoCliente(pessoaSalva.getIdPessoa());

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            ClienteFisico pessoaFisica = ClienteFisico.builder()
                    .cpf(clienteRequest.getCpf())
                    .dataNascimento(clienteRequest.getDataNascimento())
                    .idPessoa(pessoaSalva.getIdPessoa())
                    .build();

            iClienteFisicaJdbcTemplateDAO.salvarCliFisico(pessoaFisica);
        } else if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            ClienteJuridico pessoaJuridica = ClienteJuridico.builder()
                    .cnpj(clienteRequest.getCnpj())
                    .idPessoa(pessoaSalva.getIdPessoa())
                    .build();
            iClienteJuridicaJdbcTemplateDAO.salvarCliJuridico(pessoaJuridica);
        }

        return mapearPessoa(clienteRequest.getTipo_pessoa(), pessoaSalva);
    }

    /**
     * Mapeia os detalhes de uma pessoa (física ou jurídica) para um objeto de resposta.
     *
     * @param tipo_pessoa O tipo da pessoa (física ou jurídica).
     * @param pessoaSalva O objeto pessoa contendo os detalhes da pessoa salva.
     * @return Um objeto de resposta contendo os detalhes da pessoa.
     */
    private static ClienteResponseDTO mapearPessoa(TipoClienteEnum tipo_pessoa, Cliente pessoaSalva) {
        ClienteResponseDTO.ClienteResponseDTOBuilder builder = ClienteResponseDTO.builder()
                .nome(pessoaSalva.getNome())
                .email(pessoaSalva.getEmail())
                .senha(pessoaSalva.getSenha())
                .telefone(pessoaSalva.getTelefone())
                .tipo_pessoa(tipo_pessoa)
                .idPessoa(pessoaSalva.getIdPessoa());

        if (tipo_pessoa == TipoClienteEnum.FISICA) {
            builder.cpf(pessoaSalva.getCpf())
                    .dataNascimento(pessoaSalva.getDataNascimento());
        } else if (tipo_pessoa == TipoClienteEnum.JURIDICA) {
            builder.cnpj(pessoaSalva.getCnpj());
        }
        return builder.build();
    }

    /**
     * Procura uma pessoa física pelo CPF.
     *
     * @param cpf O CPF da pessoa física.
     * @return Um objeto de resposta contendo os detalhes da pessoa encontrada.
     */
    public ClienteResponseDTO procurarPessoaPorCpf(String cpf) {
        Cliente pessoaFisicaEncontrada = iClienteJdbcTemplateDAO.procurarPorCpf(cpf);
        if (Objects.isNull(pessoaFisicaEncontrada)) {
            throw new CustomException(ExceptionMessages.CPF_NAO_ENCONTRADO);
        }
        return mapearPessoa(TipoClienteEnum.FISICA, pessoaFisicaEncontrada);
    }

    public ClienteResponseDTO verificarCpfOuCnpj(String identificador) {
        ClienteResponseDTO pessoa;
        if (identificador.length() == 11) { // Assumindo que CPF tem 11 dígitos
            pessoa = procurarPessoaPorCpf(identificador);
        } else if (identificador.length() == 14) { // Assumindo que CNPJ tem 14 dígitos
            pessoa = procurarPessoaPorCnpj(identificador);
        } else {
            throw new CustomException(ExceptionMessages.IDENTIFICADOR_INVALIDO);
        }
        return pessoa;
    }

    /**
     * Procura uma pessoa jurídica pelo CNPJ.
     *
     * @param cnpj O CNPJ da pessoa jurídica.
     * @return Um objeto de resposta contendo os detalhes da pessoa encontrada.
     */
    public ClienteResponseDTO procurarPessoaPorCnpj(String cnpj) {
        Cliente pessoaJuridicaEncontrada = iClienteJdbcTemplateDAO.procurarPorCnpj(cnpj);

        if (Objects.isNull(pessoaJuridicaEncontrada)) {
            throw new CustomException(ExceptionMessages.CNPJ_NAO_ENCONTRADO);
        }
        return mapearPessoa(TipoClienteEnum.JURIDICA, pessoaJuridicaEncontrada);
    }

    /**
     * Lista todas as pessoas cadastradas.
     *
     * @return Uma lista de objetos de resposta contendo os detalhes das pessoas cadastradas.
     */
    public List<ClienteResponseDTO> listarPessoas() {
        List<Cliente> pessoasEncontradas = iClienteJdbcTemplateDAO.listarTodasPessoas();
        List<ClienteResponseDTO> clienteResponseDTO = new ArrayList<>();

        for (Cliente pessoa : pessoasEncontradas) {
            TipoClienteEnum tipoPessoa = pessoa.getCpf() != null ? TipoClienteEnum.FISICA : TipoClienteEnum.JURIDICA;
            clienteResponseDTO.add(mapearPessoa(tipoPessoa, pessoa));
        }
        return clienteResponseDTO;
    }

    /**
     * Atualiza os detalhes de uma pessoa (física ou jurídica).
     *
     * @param identificador  O CPF ou CNPJ da pessoa a ser atualizada.
     * @param clienteRequest Objeto contendo os novos detalhes da pessoa.
     * @return Um objeto de resposta contendo os detalhes da pessoa atualizada.
     */
    public ClienteResponseDTO atualizarPessoa(String identificador, ClienteAtualizarRequestDTO clienteRequest) {
        Cliente pessoaExistente;

        if (identificador.length() == 11) { // CPF
            pessoaExistente = iClienteJdbcTemplateDAO.procurarPorCpf(identificador);
        } else if (identificador.length() == 14) { // CNPJ
            pessoaExistente = iClienteJdbcTemplateDAO.procurarPorCnpj(identificador);
        } else {
            throw new CustomException(ExceptionMessages.IDENTIFICADOR_INVALIDO);
        }
        if (Objects.isNull(pessoaExistente)) {
            throw new CustomException(ExceptionMessages.CLIENTE_NAO_ENCONTRADO);
        }

        if (pessoaExistente.getTipo_pessoa() == null) {
            TipoCliente tipoCliente = iTipoClienteJdbcTemplateDAO.buscarPorNomeTipoPessoa(
                    identificador.length() == 11 ? TipoClienteEnum.FISICA.name() : TipoClienteEnum.JURIDICA.name());
            pessoaExistente.setTipo_pessoa(tipoCliente.getIdTipoPessoa());
        }

        validador.validar(clienteRequest);

        String encodedPassword = passwordEncoder.encode(clienteRequest.getSenha());

        pessoaExistente.setNome(clienteRequest.getNome());
        pessoaExistente.setEmail(clienteRequest.getEmail());
        pessoaExistente.setSenha(encodedPassword);
        pessoaExistente.setTelefone(clienteRequest.getTelefone());

        Cliente clienteAtualizado = iClienteJdbcTemplateDAO.atualizarCliente(pessoaExistente);

        return mapearPessoa(clienteAtualizado.getTipo_pessoa() == 1 ? TipoClienteEnum.FISICA : TipoClienteEnum.JURIDICA, clienteAtualizado);

    }
}