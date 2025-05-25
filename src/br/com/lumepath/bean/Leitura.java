package br.com.lumepath.bean;

/**
 * @author Ricardo
 * @version 1.0
 *
 *
 */

public interface Leitura {
    void lerSensor();
    void calcular(String tipoDeMedida, Amostra amostra);

}
