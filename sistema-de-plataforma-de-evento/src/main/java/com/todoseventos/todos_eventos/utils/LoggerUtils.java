package com.todoseventos.todos_eventos.utils;

import java.util.logging.Logger;


/**
 * Classe que registra o tempo decorrido de um método específico.
 **/
public class LoggerUtils {


    /**
     * Inícia o tempo para requisição de um método.
     * @param logger import da classe Logger.
     * @param nomeMetodo nome do método que foi chamado.
     **/
    public static void logRequestStart(Logger logger, String nomeMetodo) {
        logger.info("Início do método {} com request: {}");
    }

    /**
     * Termina o calculo do tempo após iniciado.
     * @param logger import da classe Logger.
     * @param nomeMetodo nome do método que foi chamado.
     **/
    public static void logElapsedTime(org.slf4j.Logger logger, String nomeMetodo, long startTime) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.info("Tempo decorrido no método {}: {} milissegundos", nomeMetodo, elapsedTime);
    }

    //Talves não use esse:
    public static void logError(org.slf4j.Logger logger, String methodName, Object request, Exception e) {
        logger.error("Erro ao executar o método {}: request: {}", methodName, request, e);
    }
}
