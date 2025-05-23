package br.com.lumepath.bean;

import java.util.List;

/**
 * Interface que define os comportamentos esperados de um sensor genérico.
 *
 * <p>Essa interface pode ser implementada por sensores de diferentes tipos,
 * como sensores de distância (Laser, ultrassônicos), câmeras com visão computacional, entre outros.</p>
 *
 * <p>Ela define métodos essenciais para o ciclo de vida do sensor,
 * bem como para leitura, calibração, armazenamento e cálculo de dados.</p>
 *
 * <p>O modelo de uso típico inclui: inicialização, calibração, leitura de dados,
 * armazenamento das leituras e, posteriormente, cálculos sobre os dados obtidos.</p>
 *
 * @author Ricardo
 * @version 1.2
 */
public interface Sensor {

    /**
     * Inicializa o sensor, preparando-o para operação.
     * Deve ser chamado antes de qualquer leitura ou armazenamento de dados.
     */
    void iniciar();

    /**
     * Realiza a leitura dos dados brutos do sensor.
     *
     * @return um {@code double} representando o valor lido na unidade apropriada do sensor.
     */
    double lerDados();

    /**
     * Armazena os dados brutos obtidos na última leitura do sensor em uma lista fornecida.
     *
     * @param lista a lista onde os dados brutos devem ser armazenados.
     */
    void armazenarDado(List<Double> lista);

    void armazenarDado(Double dado);

    /**
     * Reseta o estado interno do sensor para sua configuração inicial.
     *
     * <p>Este metodo geralmente limpa os dados armazenados, desfaz calibrações anteriores
     * e prepara o sensor para um novo ciclo de operação.</p>
     */
    void reset();

    /**
     * Realiza cálculos sobre os dados lidos e armazenados pelo sensor.
     *
     * <p>Os cálculos podem incluir transformações físicas, como conversão de unidades,
     * média de valores ou derivação de medidas compostas.</p>
     *
     * @param tipoDeMedida a medida a ser calculada (por exemplo, "altura", "comprimento" ou "profundidade").
     * @param amostra o objeto {@link Amostra} onde o resultado do cálculo será armazenado.
     */
    void calcular(String tipoDeMedida, Amostra amostra);

    /**
     * Encerra o uso do sensor, liberando recursos alocados e atualizando o estado interno.
     *
     * <p>Deve ser chamado quando o sensor não for mais necessário, garantindo a liberação adequada de recursos.</p>
     */
    void encerrar();

    /**
     * Realiza a calibração do sensor para garantir maior precisão na leitura de dados.
     *
     * <p>Este metodo ajusta os parâmetros internos do sensor conforme especificações do hardware.</p>
     */
    void calibrar();

    /**
     * Obtém a velocidade atual do slider, um parâmetro crítico para os cálculos baseados no tempo de detecção.
     *
     * @return a velocidade atual do slider, em metros por segundo (m/s).
     */
    double getVelocidadeAtualDoSlider();
}
