package br.com.lumepath.bean;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class SensorOpenCV implements Sensor {
    private String portaSerial;
    private boolean ativo = false;
    private double altura;
    private double largura;

    public SensorOpenCV(String portaSerial) {
        setPortaSerial(portaSerial);
    }

    public String getPortaSerial() {
        return portaSerial;
    }

    public void setPortaSerial(String portaSerial) {
        if (portaSerial == null || portaSerial.trim().isEmpty()) {
            throw new IllegalArgumentException("Porta serial não pode ser nula ou vazia.");
        }
        if (!(portaSerial.matches("COM[0-9]+") || portaSerial.matches("/dev/tty\\w+"))) {
            throw new IllegalArgumentException("Formato de porta serial inválido: " + portaSerial);
        }
        this.portaSerial = portaSerial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo() {
        this.ativo = !this.ativo;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        if (altura <= 0) {
            this.altura = altura;
        } else {
            throw new IllegalArgumentException("Altura deve ser maior que zero.");
        }

    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        if (largura <= 0) {
            this.largura = largura;
        } else {
            throw new IllegalArgumentException("Largura deve ser maior que zero.");
        }

    }


    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        lerDados(leitor);

    }

    @Override
    public double lerDados(Leitor leitor) {
        try {
            setAltura(Double.parseDouble(JOptionPane.showInputDialog("Digite a altura da amostra: ")));
            setLargura(Double.parseDouble(JOptionPane.showInputDialog("Digite a largura da amostra: ")));
        }
        catch (Exception e) {
            System.out.println("Erro ao ler dados: " + e.getMessage());
        }
        return 0; // retorno ignorado nesse metodo, ele seta a altura e a largura da amostra usando JOptionPane durante os testes mockados
    }

    @Override
    public void reset() {
        setAltura(0);
        setLargura(0);
    }

    @Override
    public void encerrar() {
        reset();
        setAtivo();
    }

    @Override
    public void calibrar() {
        JOptionPane.showMessageDialog(null,"Calibrado com sucesso!");
    }

    @Override
    public void enviarDadosAoLeitor(Leitor leitor) {
        leitor.setLeituraCameraAltura(getAltura());
        leitor.setLeituraCameraComprimento(getLargura());
    }

    /**
 * A câmera fica parada no aparelho, então é constantemente zero
 * */
    @Override
    public double getVelocidadeAtualDoSlider() {
        return 0;
    }
}
