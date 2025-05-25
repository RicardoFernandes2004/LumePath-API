package br.com.lumepath.bean;

/**
 * Interface que define o contrato para componentes responsáveis por realizar
 * a leitura de dados e o cálculo de medidas associados a uma {@link Amostra}.
 *
 * <p>Implementações desta interface devem garantir que os dados sejam
 * corretamente capturados e processados conforme o tipo de medida solicitado.</p>
 *
 * @author Ricardo
 * @version 1.0
 */
public interface Leitura {

    /**
     * Executa o ciclo de leitura de dados do sensor.
     *
     * <p>Implementações devem garantir a captura e, se necessário, o envio
     * dos dados obtidos para a {@link Amostra} ou outro componente responsável.</p>
     */
    void lerSensor();

    /**
     * Realiza o cálculo e atribuição de uma medida específica na {@link Amostra}.
     *
     * @param tipoDeMedida o tipo da medida a ser calculada (por exemplo: "altura", "comprimento", "profundidade").
     * @param amostra a amostra onde o resultado será armazenado.
     */
    void calcular(String tipoDeMedida, Amostra amostra);
}
