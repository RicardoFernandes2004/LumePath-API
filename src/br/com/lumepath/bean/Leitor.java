package br.com.lumepath.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Leitor {
    private Sensor sensor;
    private double precisao;
    private LocalDateTime ultimaLeitura;
    private final List<Double> dadosProfundidade = new ArrayList<>();
    private final List<Double> dadosComprimento = new ArrayList<>();



}
