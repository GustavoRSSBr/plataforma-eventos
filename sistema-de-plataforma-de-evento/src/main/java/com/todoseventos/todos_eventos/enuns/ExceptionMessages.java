package com.todoseventos.todos_eventos.enuns;

public class ExceptionMessages {

    public static final String EVENTO_NAO_ENCONTRADO = "Evento não encontrado!";
    public static final String ENDERECO_NAO_ENCONTRADO = "Endereço não encontrado: ";
    public static final String ID_PESSOA_NAO_INFORMADO = "O ID da Pessoa não foi informado";
    public static final String VALOR_DEPOSITO_INVALIDO = "Valor de depósito inválido. Digite novamente o valor do depósito.";
    public static final String ESTRATEGIA_NAO_ENCONTRADA = "Nenhuma estratégia de validação encontrada para ";
    public static final String ESTRATEGIA_DUPLICADA = "Já existe uma estratégia registrada para a classe: ";
    public static final String TIPO_CATEGORIA_INVALIDO = "Tipo de categoria inválido!";
    public static final String CATEGORIA_INVALIDA = "Categoria Inválida!";
    public static final String VALOR_INVALIDO = "O valor do ingresso deve ser positivo e o valor máximo aceito é 10.000";
    public static final String CEP_INVALIDO = "CEP inválido!";
    public static final String CEP_INEXISTENTE = "CEP inexistente!";
    public static final String NUMERO_INVALIDO = "O campo número aceita apenas caracteres numéricos.";
    public static final String LIMITE_INVALIDO = "O limite de ocupação deve ser positivo e a ocupação máxima é de 200.000";
    public static final String ERRO_BUSCAR_EVENTOS = "Erro ao buscar eventos: ";
    public static final String ERRO_BUSCAR_CATEGORIA_EVENTO = "Erro ao buscar categoria do evento: ";
    public static final String ERRO_BUSCAR_ENDERECO_EVENTO = "Erro ao buscar endereço do evento: ";
    public static final String EMAIL_INVALIDO = "E-mail inválido!";
    public static final String CAMPO_FIXO = "Todos os campos devem ser preenchidos";
    public static final String NOME_INVALIDO = "O nome deve possuir, no mínimo, 10 caracteres.";
    public static final String DATA_INICIO_INVALIDA = "A data do seu evento não pode ser anterior à data atual.";
    public static final String DATA_FINAL_INVALIDA = "A data do seu evento não pode ser superior a 3 anos da data atual.";
    public static final String DATA_EVENTO_INVALIDA = "O horário de encerramento do seu evento não pode ser anterior ao horário de início.";
    public static final String DESCRICAO_INVALIDA = "A descrição deve possuir, no mínimo, 20 caracteres";
    public static final String TELEFONE_INVALIDO = "Número de telefone inválido!";
    public static final String DATA_NASCIMENTO_INVALIDA = "Data de nascimento inválida!";
    public static final String CPF_INVALIDO = "CPF inválido! Verifique o dado informado.";
    public static final String CPF_NAO_ENCONTRADO = "CPF não encontrado!";
    public static final String CPF_JA_CADASTRADO = "CPF já cadastrado!";
    public static final String CNPJ_INVALIDO = "CNPJ inválido! Verifique o dado informado.";
    public static final String CNPJ_JA_CADASTRADO = "CNPJ já cadastrado!";
    public static final String CNPJ_NAO_ENCONTRADO = "CNPJ não encontrado";
    public static final String IDENTIFICADOR_INVALIDO = "Identificador inválido!";
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado!";
    public static final String CEP_EM_BRANCO = "CEP não pode estar em branco:";
    public static final String SENHA_INVALIDA = "Senha inválida: a senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial.";
    public static final String ERRO_ENVIAR_EMAIL = "Erro ao enviar e-mail:";
    public static final String ERRO_ENVIAR_EMAIL_CANCELAMENTO = "Erro ao enviar e-mail de cancelamento:";
    public static final String EMAIL_SENHA = "Email ou senha inválidos!";
    public static final String ERRO_INTERNO = "Ocorreu um erro interno. Por favor, tente novamente mais tarde.";

    public static final String TOKEN_EMAIL = "Usuário não encontrado com e-mail: ";

    public static final String DADO_INVALIDO = "Dados inválidos. Revise e informe os dados novamente.";

    public static final String CREDENCIAIS_INVALIDAS = "Credenciais inválidas. Revise e informe os dados novamente.";

    private ExceptionMessages(){
    }
}
