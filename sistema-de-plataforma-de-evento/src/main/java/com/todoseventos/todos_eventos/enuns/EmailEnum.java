package com.todoseventos.todos_eventos.enuns;

public enum EmailEnum {

    INSCRICAO_GARANTIDA(
                    "<html>" +
                            "<head>" +
                            "<meta charset='UTF-8'>" +
                            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                            "<title>Inscrição Garantida!</title>" +
                            "<style>" +
                            "body { font-family: Arial, sans-serif; background-color: #f4f4f9; color: #333; margin: 0; padding: 20px; }" +
                            ".container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                            "h1 { color: #4CAF50; }" +
                            "p { line-height: 1.6; }" +
                            ".event-info { margin-top: 20px; }" +
                            ".event-info p { margin: 5px 0; }" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class='container'>" +
                            "<h1>Inscrição Garantida!</h1>" +
                            "<p>Olá, {nomePessoa},</p>" +
                            "<p>Estamos muito felizes em informar que sua inscrição no evento <strong>{nomeEvento}</strong> foi garantida!</p>" +
                            "<div class='event-info'>" +
                            "<h3>INFORMAÇÕES DO EVENTO</h3>" +
                            "<p><strong>Data:</strong> {dataEvento}</p>" +
                            "<p><strong>Local:</strong> {localEvento}</p>" +
                            "</div>" +
                            "<p>Agradecemos por se inscrever e esperamos vê-lo lá!</p>" +
                            "<p>Atenciosamente,</p>" +
                            "<p><em>Equipe de Organização do Evento</em></p>" +
                            "</div>" +
                            "</body>" +
                            "</html>"
    ),
    EVENTO_CANCELADO(
            "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "<title>Evento Cancelado</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f4f4f9; color: #333; margin: 0; padding: 20px; }" +
                    ".container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                    "h1 { color: #FF0000; }" +
                    "p { line-height: 1.6; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Evento Cancelado</h1>" +
                    "<p>Olá, {nomePessoa},</p>" +
                    "<p>Informamos que o evento <strong>{nomeEvento}</strong> foi cancelado pelo produtor.</p>" +
                    "<p>O reembolso do valor do ingresso será efetuado no mesmo método de pagamento utilizado na compra.</p>" +
                    "<p>Pedimos desculpas pelo inconveniente e agradecemos pela sua compreensão.</p>" +
                    "<p>Atenciosamente,</p>" +
                    "<p><em>Equipe de Organização do Evento</em></p>" +
                    "</div>" +
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