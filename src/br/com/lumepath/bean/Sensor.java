package br.com.lumepath.bean;


/**
 * Interface que define os comportamentos esperados de um sensor genérico.
 *
 * <p>Esta interface pode ser implementada por diferentes tipos de sensores,
 * como sensores de distância (laser, ultrassônicos), câmeras com visão computacional, entre outros.</p>
 *
 * <p>Ela estabelece métodos essenciais para o ciclo de vida do sensor,
 * incluindo inicialização, leitura de dados, calibração, envio de dados ao leitor e encerramento.</p>
 *
 * <p>O uso típico envolve: inicialização, calibração, leitura de dados,
 * envio das leituras ao leitor e, posteriormente, cálculos sobre os dados obtidos.</p>
 *
 * @author Ricardo
 * @version 1.2
 */
public interface Sensor {

    /**
     * Inicializa o sensor, preparando-o para operação.
     * Deve ser chamado antes de qualquer leitura ou armazenamento de dados.
     *
     * @param leitor o objeto {@link Leitor} que receberá os dados do sensor.
     */
    void iniciar(Leitor leitor);

    /**
     * Realiza a leitura dos dados brutos do sensor.
     *
     * @param leitor o objeto {@link Leitor} que receberá os dados lidos.
     * @return um {@code double} representando o valor lido na unidade apropriada do sensor.
     */
    double lerDados(Leitor leitor);

    /**
     * Reseta o estado interno do sensor para sua configuração inicial.
     *
     * <p>Este metodo geralmente limpa os dados armazenados, desfaz calibrações anteriores
     * e prepara o sensor para um novo ciclo de operação.</p>
     */
    void reset();

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
     * Envia os dados lidos do sensor para o objeto {@link Leitor}.
     *
     * @param leitor o objeto que receberá os dados do sensor.
     */
    void enviarDadosAoLeitor(Leitor leitor);

    /**
     * Obtém a velocidade atual do slider, um parâmetro crítico para os cálculos baseados no tempo de detecção.
     *
     * @return a velocidade atual do slider, em metros por segundo (m/s).
     */
    double getVelocidadeAtualDoSlider();
}
