package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.ClienteResponse;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import com.todoseventos.todos_eventos.dao.IClienteDao;
import com.todoseventos.todos_eventos.dao.IClienteFisicaDao;
import com.todoseventos.todos_eventos.dao.IClienteJuridicaDao;
import com.todoseventos.todos_eventos.dao.ITipoClienteDao;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisicaModel;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridicaModel;
import com.todoseventos.todos_eventos.model.cliente.ClienteModel;
import com.todoseventos.todos_eventos.model.cliente.TipoClienteModel;
import com.todoseventos.todos_eventos.utils.Validacoes;
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
    private IClienteDao IClienteDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ITipoClienteDao ITipoClienteDao;

    @Autowired
    private IClienteFisicaDao IClienteFisicaDao;

    @Autowired
    private IClienteJuridicaDao IClienteJuridicaDao;

    /**
     * Cadastra uma nova pessoa (física ou jurídica).
     * @param clienteRequest Objeto contendo os detalhes da pessoa a ser cadastrada.
     * @return Um objeto de resposta contendo os detalhes da pessoa cadastrada.
     */
    public ClienteResponse cadastrarNovaPessoa(ClienteRequest clienteRequest) {

        if (clienteRequest.getTipo_pessoa() == null) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        TipoClienteModel tipoClienteModel = ITipoClienteDao.buscarPorNomeTipoPessoa(clienteRequest.getTipo_pessoa().name());

        if (Objects.isNull(tipoClienteModel)) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        validarDados(clienteRequest);

        clienteRequest.setTelefone(validacoes.formatarNumeroTelefone(clienteRequest.getTelefone()));
        String encodedPassword = passwordEncoder.encode(clienteRequest.getSenha());

        ClienteModel pessoa = ClienteModel.builder()
                .nome(clienteRequest.getNome())
                .email(clienteRequest.getEmail())
                .senha(encodedPassword)
                .telefone(clienteRequest.getTelefone())
                .tipo_pessoa(tipoClienteModel.getIdTipoPessoa())
                .build();

        ClienteModel pessoaSalva = IClienteDao.salvarCliente(pessoa);

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            ClienteFisicaModel pessoaFisica = ClienteFisicaModel.builder()
                    .cpf(clienteRequest.getCpf())
                    .dataNascimento(clienteRequest.getDataNascimento())
                    .idPessoa(pessoaSalva.getIdPessoa())
                    .build();
            IClienteFisicaDao.salvarCliFisico(pessoaFisica);

        } else if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            ClienteJuridicaModel pessoaJuridica = ClienteJuridicaModel.builder()
                    .cnpj(clienteRequest.getCnpj())
                    .idPessoa(pessoaSalva.getIdPessoa())
                    .build();
            IClienteJuridicaDao.salvarCliJuridico(pessoaJuridica);
        }
        return mapearPessoa(clienteRequest.getTipo_pessoa(), pessoaSalva);
    }

    /**
     * Valida os dados do cliente.
     * @param clienteRequest Objeto contendo os detalhes do cliente a ser validado.
     */
    private void validarDados(ClienteRequest clienteRequest) {
        if (!validacoes.validarEmail(clienteRequest.getEmail())) {
            throw new CustomException(ExceptionMessages.EMAIL_INVALIDO);
        }

        if (!validacoes.validarNumeroTelefone(clienteRequest.getTelefone())) {
            throw new CustomException(ExceptionMessages.TELEFONE_INVALIDO);
        }

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA &&
                !validacoes.validarDataNascimento(clienteRequest.getDataNascimento())) {
            throw new CustomException(ExceptionMessages.DATA_NASCIMENTO_INVALIDA);
        }

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            if (!validacoes.isCpfValid(clienteRequest.getCpf())) {
                throw new CustomException(ExceptionMessages.CPF_INVALIDO);
            }

            ClienteModel pessoaExistente = IClienteDao.procurarPorCpf(clienteRequest.getCpf());
            if (pessoaExistente != null) {
                throw new CustomException(ExceptionMessages.CPF_JA_CADASTRADO);
            }

        } else if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            if (!validacoes.isCnpjValid(clienteRequest.getCnpj())) {
                throw new CustomException(ExceptionMessages.CNPJ_INVALIDO);
            }

            ClienteModel pessoaExistente = IClienteDao.procurarPorCnpj(clienteRequest.getCnpj());
            if (pessoaExistente != null) {
                throw new CustomException(ExceptionMessages.CNPJ_JA_CADASTRADO);
            }
        }
    }

    /**
     * Mapeia os detalhes de uma pessoa (física ou jurídica) para um objeto de resposta.
     * @param tipo_pessoa O tipo da pessoa (física ou jurídica).
     * @param pessoaSalva O objeto pessoa contendo os detalhes da pessoa salva.
     * @return Um objeto de resposta contendo os detalhes da pessoa.
     */
    private static ClienteResponse mapearPessoa(TipoClienteEnum tipo_pessoa, ClienteModel pessoaSalva) {
        ClienteResponse.ClienteResponseBuilder builder = ClienteResponse.builder()
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
     * @param cpf O CPF da pessoa física.
     * @return Um objeto de resposta contendo os detalhes da pessoa encontrada.
     */
    public ClienteResponse procurarPessoaPorCpf(String cpf) {
        ClienteModel pessoaFisicaEncontrada = IClienteDao.procurarPorCpf(cpf);
        if (Objects.isNull(pessoaFisicaEncontrada)) {
            throw new CustomException(ExceptionMessages.CPF_INVALIDO);
        }
        return mapearPessoa(TipoClienteEnum.FISICA, pessoaFisicaEncontrada);
    }

    public ClienteResponse verificarCpfOuCnpj(String identificador) {
        ClienteResponse pessoa;
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
     * @param cnpj O CNPJ da pessoa jurídica.
     * @return Um objeto de resposta contendo os detalhes da pessoa encontrada.
     */
    public ClienteResponse procurarPessoaPorCnpj(String cnpj) {
        ClienteModel pessoaJuridicaEncontrada = IClienteDao.procurarPorCnpj(cnpj);

        if (Objects.isNull(pessoaJuridicaEncontrada)) {
            throw new CustomException(ExceptionMessages.CNPJ_JA_CADASTRADO);
        }
        return mapearPessoa(TipoClienteEnum.JURIDICA, pessoaJuridicaEncontrada);
    }

    /**
     * Lista todas as pessoas cadastradas.
     * @return Uma lista de objetos de resposta contendo os detalhes das pessoas cadastradas.
     */
    public List<ClienteResponse> listarPessoas() {
        List<ClienteModel> pessoasEncontradas = IClienteDao.listarTodasPessoas();
        List<ClienteResponse> clienteResponse = new ArrayList<>();

        for (ClienteModel pessoa : pessoasEncontradas) {
            TipoClienteEnum tipoPessoa = pessoa.getCpf() != null ? TipoClienteEnum.FISICA : TipoClienteEnum.JURIDICA;
            clienteResponse.add(mapearPessoa(tipoPessoa, pessoa));
        }
        return clienteResponse;
    }

    /**
     * Atualiza os detalhes de uma pessoa (física ou jurídica).
     * @param identificador O CPF ou CNPJ da pessoa a ser atualizada.
     * @param clienteRequest Objeto contendo os novos detalhes da pessoa.
     * @return Um objeto de resposta contendo os detalhes da pessoa atualizada.
     */
    public ClienteResponse atualizarPessoa(String identificador, ClienteRequest clienteRequest) {
        ClienteModel pessoaExistente;


        if (identificador.length() == 11) { // CPF
            pessoaExistente = IClienteDao.procurarPorCpf(identificador);
        } else if (identificador.length() == 14) { // CNPJ
            pessoaExistente = IClienteDao.procurarPorCnpj(identificador);
        } else {
            throw new CustomException(ExceptionMessages.IDENTIFICADOR_INVALIDO);
        }

        if (Objects.isNull(pessoaExistente)) {
            throw new CustomException(ExceptionMessages.CLIENTE_NAO_ENCONTRADO);
        }

        TipoClienteModel tipoClienteModel = ITipoClienteDao.buscarPorNomeTipoPessoa(clienteRequest.getTipo_pessoa().name());
        String encodedPassword = passwordEncoder.encode(clienteRequest.getSenha());

        pessoaExistente.setNome(clienteRequest.getNome());
        pessoaExistente.setEmail(clienteRequest.getEmail());
        pessoaExistente.setSenha(encodedPassword);
        pessoaExistente.setTelefone(clienteRequest.getTelefone());
        pessoaExistente.setTipo_pessoa(tipoClienteModel.getIdTipoPessoa());

        ClienteModel clienteAtualizado = IClienteDao.atualizarCliente(pessoaExistente);

        if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            ClienteFisicaModel pessoaFisica = IClienteFisicaDao.procurarCpf(identificador);
            if (pessoaFisica != null) {
                pessoaFisica.setIdPessoa(clienteAtualizado.getIdPessoa());
                pessoaFisica.setCpf(clienteRequest.getCpf());
                pessoaFisica.setDataNascimento(clienteRequest.getDataNascimento());
                IClienteFisicaDao.atualizarCliFisico(pessoaFisica);
            }
        } else if (clienteRequest.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            ClienteJuridicaModel pessoaJuridica = IClienteJuridicaDao.procurarCnpj(identificador);
            if (pessoaJuridica != null) {
                pessoaJuridica.setIdPessoa(clienteAtualizado.getIdPessoa());
                pessoaJuridica.setCnpj(clienteRequest.getCnpj());
                IClienteJuridicaDao.atualizarJuridico(pessoaJuridica);
            }
        }
        return mapearPessoa(clienteRequest.getTipo_pessoa(), clienteAtualizado);
    }
}