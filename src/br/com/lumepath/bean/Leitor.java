package br.com.lumepath.bean;

import javax.swing.*;
import java.time.LocalDateTime;

public class Leitor implements Leitura {
    private final Sensor sensor;
    private final Amostra amostra;
    private double precisao;
    private LocalDateTime ultimaLeitura;
    private boolean detectando = false;
    private double leituraAltura;
    private double leituraComprimento;
    private double leituraProfundidade;
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

    public void calcPrecisao() {
        double erroAltura = Math.abs(leituraCameraAltura - leituraAltura);
        double erroComprimento = Math.abs(leituraCameraComprimento - leituraComprimento);
        this.precisao = (erroAltura + erroComprimento) / 2;
    }

    public double getPrecisao() {
        return precisao;
    }

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

    @Override
    public void lerSensor() {
        sensor.iniciar(this);
        //sensor.enviarDadosAoLeitor(this);
        enviarDadosAmostra(amostra);
        sensor.encerrar();
        setUltimaLeitura();
    }

    public void enviarDadosAmostra(Amostra amostra) {
        if (!(sensor instanceof SensorOpenCV)) {
            calcular("altura", amostra);
            calcular("comprimento", amostra);
            calcular("profundidade", amostra);
        }
        // Se for SensorOpenCV, não faz nada:
        // pois a câmera não preenche as leituras que alimentam a amostra
    }

}
