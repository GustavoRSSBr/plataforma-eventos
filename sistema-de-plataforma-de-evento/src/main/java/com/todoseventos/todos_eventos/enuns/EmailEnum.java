package com.todoseventos.todos_eventos.enuns;

public enum EmailEnum {

    INSCRICAO_GARANTIDA(
            "<html>" +
                    "<body>" +
                    "<h1>Inscrição Garantida!</h1>" +
                    "<p>Olá, {nomePessoa},</p>" +
                    "<p>Sua inscrição no evento, {nomeEvento}, foi garantida! Não esqueça de confirmar a sua presença no botão abaixo.</p>" +
                    "<h3>INFORMAÇÕES DO EVENTO</h3>" +
                    "<p><strong>Data:</strong> {dataEvento}</p>" +
                    "<p><strong>Local:</strong> {localEvento}</p>" +
                    "</body>" +
                    "</html>"
    ),
    CONFIRMACAO_DE_PARTICIPACAO(
            "<html>" +
                    "<body>" +
                    "<h1>Confirmação de Participação</h1>" +
                    "<p>Olá, {nomePessoa},</p>" +
                    "<p>Agradecemos a confirmação no evento, {nomeEvento}. Aguardamos a sua presença!</p>" +
                    "<h3>INFORMAÇÕES DO EVENTO</h3>" +
                    "<p><strong>Data:</strong> {dataEvento}</p>" +
                    "<p><strong>Local:</strong> {rua}, {numero}, {bairro}, {cidade}, {uf}</p>" +
                    "</body>" +
                    "</html>"
    ),
    EVENTO_CANCELADO(
            "<html>" +
                    "<body>" +
                    "<h1>Evento Cancelado</h1>" +
                    "<p>Olá, {nomePessoa},</p>" +
                    "<p>Informamos que o evento, {nomeEvento}, foi cancelado pelo produtor.</p>" +
                    "<p>Pedimos desculpas pelo inconveniente.</p>" +
                    "</body>" +
                    "</html>"
    );

    private final String modelo;

    EmailEnum(String modelo)
    {
        this.modelo = modelo;
    }

    public String getModelo()
    {
        return modelo;
    }

}