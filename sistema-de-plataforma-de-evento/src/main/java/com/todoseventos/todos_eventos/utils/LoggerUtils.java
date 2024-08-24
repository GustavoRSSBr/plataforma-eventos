package com.todoseventos.todos_eventos.utils;

import java.util.logging.Logger;


/**
 * Classe que registra o tempo decorrido de um método específico.
 **/
public class LoggerUtils {

    /**
     * Termina o calculo do tempo após iniciado.
     *
     * @param logger     import da classe Logger.
     * @param nomeMetodo nome do método que foi chamado.
     **/
    public static void logElapsedTime(org.slf4j.Logger logger, String nomeMetodo, long startTime) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.info("Tempo decorrido no método {}: {} milissegundos", nomeMetodo, elapsedTime);
    }
}
