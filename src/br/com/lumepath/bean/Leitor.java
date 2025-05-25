package br.com.lumepath.bean;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * Representa o componente responsável por realizar a leitura de dados do {@link Sensor},
 * associando os resultados a uma {@link Amostra} e calculando a precisão com base na comparação
 * entre sensores de diferentes naturezas (e.g., laser vs. câmera).
 *
 * <p>Gerencia o estado de detecção, armazena as últimas leituras e controla o envio
 * das medições para a amostra.</p>
 * @author Ricardo
 * @version 1.3
 */

public class Leitor implements Leitura {

    /** Sensor associado ao leitor, responsável pela obtenção das medições. */
    private final Sensor sensor;

    /** Amostra associada ao leitor, que receberá os dados processados. */
    private final Amostra amostra;

    /** Precisão calculada com base na diferença entre as leituras do sensor e da câmera. */
    private double precisao;

    /** Marca a data e hora da última leitura realizada pelo sensor. */
    private LocalDateTime ultimaLeitura;

    /** Indica se o sensor está atualmente em modo de detecção. */
    private boolean detectando = false;

    /** Leituras brutas obtidas do sensor: altura, comprimento e profundidade. */
    private double leituraAltura;
    private double leituraComprimento;
    private double leituraProfundidade;

    /** Leituras complementares obtidas via câmera, usadas para verificar a precisão da medição. */
    private double leituraCameraAltura;
    private double leituraCameraComprimento;

    public Leitor(Sensor sensor, Amostra amostra) {
        this.sensor = sensor;
        this.amostra = amostra;
    }

    public LocalDateTime getUltimaLeitura() {
        return ultimaLeitura;
    }

    public void setUltimaLeitura() {
        this.ultimaLeitura = LocalDateTime.now();
    }

    public boolean isDetectando() {
        return detectando;
    }

    public void setDetectando() {
        this.detectando = !this.detectando;
    }

    public double getLeituraCameraAltura() {
        return leituraCameraAltura;
    }

    public void setLeituraCameraAltura(double leituraCameraAltura) {
        this.leituraCameraAltura = leituraCameraAltura;
    }

    public double getLeituraCameraComprimento() {
        return leituraCameraComprimento;
    }

    public void setLeituraCameraComprimento(double leituraCameraComprimento) {
        this.leituraCameraComprimento = leituraCameraComprimento;
    }

    public void setLeituraAltura(double leituraAltura) {
        this.leituraAltura = leituraAltura;
    }

    public void setLeituraComprimento(double leituraComprimento) {
        this.leituraComprimento = leituraComprimento;
    }

    public void setLeituraProfundidade(double leituraProfundidade) {
        this.leituraProfundidade = leituraProfundidade;
    }


    /**
     * Calcula a precisão da medição com base na diferença entre as leituras da câmera
     * e do sensor principal.
     *
     * <p>O cálculo da precisão é a média absoluta entre os erros de altura e comprimento.</p>
     */
    public void calcPrecisao() {
        double erroAltura = Math.abs(leituraCameraAltura - leituraAltura);
        double erroComprimento = Math.abs(leituraCameraComprimento - leituraComprimento);
        this.precisao = (erroAltura + erroComprimento) / 2;
    }

    public double getPrecisao() {
        return precisao;
    }

    /**
     * Realiza o cálculo e atribuição de uma medida específica da amostra.
     *
     * @param tipoDeMedida tipo da medida a ser calculada: "altura", "comprimento" ou "profundidade".
     * @param amostra a {@link Amostra} onde o resultado será armazenado.
     * @throws IllegalArgumentException se o tipo de medida for inválido.
     */
    @Override
    public void calcular(String tipoDeMedida, Amostra amostra) {
        switch (tipoDeMedida) {
            case "altura":
                amostra.setAltura(leituraAltura);
                break;
            case "comprimento":
                amostra.setComprimento(leituraComprimento);
                break;
            case "profundidade":
                amostra.setProfundidade(leituraProfundidade);
                break;
            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipoDeMedida);
        }
    }

    /**
     * Executa o ciclo completo de leitura do sensor: inicialização, leitura,
     * envio dos dados para a amostra e encerramento.
     *
     * <p>Ao final do ciclo, atualiza a data/hora da última leitura realizada.</p>
     */
    @Override
    public void lerSensor() {
        sensor.iniciar(this);
        //sensor.enviarDadosAoLeitor(this);
        enviarDadosAmostra(amostra);
        sensor.encerrar();
        setUltimaLeitura();
    }


    /**
     * Envia os dados obtidos pelo leitor para a {@link Amostra}.
     *
     * <p>Se for SensorOpenCV, não faz nada pois a coleta da câmera é apenas para deduzir margem de erro do laser</p>
     *
     * @param amostra a amostra que receberá os dados calculados.
     */
    public void enviarDadosAmostra(Amostra amostra) {
        if (!(sensor instanceof SensorOpenCV)) {
            calcular("altura", amostra);
            calcular("comprimento", amostra);
            calcular("profundidade", amostra);
        }
    }

}
