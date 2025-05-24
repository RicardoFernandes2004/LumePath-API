package br.com.lumepath.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Leitor implements Leitura{
    private Sensor sensor;
    private double precisao;
    private LocalDateTime ultimaLeitura;
    private boolean detectando = false;
    private final List<Double> dadosProfundidade = new ArrayList<>();
    private final List<Double> dadosComprimento = new ArrayList<>();
    private final List<Double> dadosAltura = new ArrayList<>();
    private double leituraCameraAltura;
    private double leituraCameraComprimento;

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

    public List<Double> getDadosAltura() {
        return dadosAltura;
    }

    public void addDadosAltura(Double Altura) {
        this.dadosAltura.add(Altura);
    }

    public List<Double> getDadosComprimento() {
        return dadosComprimento;
    }

    public void addDadosComprimento(Double Comprimento) {
        this.dadosComprimento.add(Comprimento);
    }

    public List<Double> getDadosProfundidade() {
        return dadosProfundidade;
    }

    public void addDadosProfundidade(Double Profundidade) {
        this.dadosProfundidade.add(Profundidade);
    }

    /**
     * Realiza o cálculo de uma medida com base nos dados armazenados e na velocidade do slider.
     * O metodo isEmpty() é utilizado para garantir a segurança na operação de cálculo,     prevenindo a realização de operações aritméticas ou lógicas sobre listas vazias, o que poderia gerar resultados incorretos ou até exceções.
     *
     * @param tipoDeMedida "altura", "comprimento" ou "profundidade".
     * @param amostra objeto {@link Amostra} onde o resultado será armazenado.
     * @throws IllegalArgumentException se o tipo de medida for inválido.
     */
    @Override
    public void calcular(String tipoDeMedida, Amostra amostra) {
        switch (tipoDeMedida) {
            case "altura":
                if (!dadosAltura.isEmpty()) {
                    amostra.setAltura(sensor.getVelocidadeAtualDoSlider() * (getDadosAltura().size() - 1));
                } else {
                    amostra.setAltura(0);
                }
                break;
            case "comprimento":
                if (!dadosComprimento.isEmpty()) {
                    amostra.setComprimento(sensor.getVelocidadeAtualDoSlider() * (getDadosComprimento().size() - 1));
                } else {
                    amostra.setComprimento(0);
                }
                break;
            case "profundidade":
                if (!dadosProfundidade.isEmpty()) {
                    amostra.setProfundidade(sensor.getVelocidadeAtualDoSlider() * (getDadosProfundidade().size() - 1));
                } else {
                    amostra.setProfundidade(0);
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipoDeMedida);
        }
    }

    @Override
    public void lerSensor(Sensor sensor) {


    }

    @Override
    public void armazenarDado() {



    }
}
