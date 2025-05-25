package br.com.lumepath.bean;

import javax.swing.*;

/**
 * Implementação da interface {@link Sensor} para sensores baseados em tecnologia laser.
 * <p>
 * Atualmente realiza a leitura mockada via JOptionPane.
 * <p>
 * Futuramente será implementada com coleta automática contínua,
 * utilizando estruturas de dados como Map e List para armazenar medições
 * e realizar cálculos com base na equação S = v * Δt.
 * </p>
 */
public class SensorLaser implements Sensor {

    private String portaSerial;
    private double velocidadeAtualDoSlider;
    private boolean calibrado = false;
    private boolean ativo = false;

    private double altura;
    private double comprimento;
    private double profundidade;

    /**
     * Construtor que define a porta serial utilizada pelo sensor.
     *
     * @param portaSerial a porta serial para comunicação com o hardware.
     * @throws IllegalArgumentException se a porta for nula, vazia ou não estiver no formato esperado.
     */
    public SensorLaser(String portaSerial) {
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

    /**
     * Ativa ou desativa o sensor. Se não calibrado, realiza calibração automaticamente.
     */
    public void setAtivo() {
        if (!isCalibrado()) {
            calibrar();
        }
        this.ativo = !this.ativo;
    }

    public boolean isCalibrado() {
        return calibrado;
    }

    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        try {
            altura = Double.parseDouble(JOptionPane.showInputDialog("Digite a altura da amostra: "));
            comprimento = Double.parseDouble(JOptionPane.showInputDialog("Digite o comprimento da amostra: "));
            profundidade = Double.parseDouble(JOptionPane.showInputDialog("Digite a profundidade da amostra: "));



            leitor.setDetectando(); // ativa a detecção

            // Envia diretamente os dados lidos para o leitor
            leitor.setLeituraAltura(altura);
            leitor.setLeituraComprimento(comprimento);
            leitor.setLeituraProfundidade(profundidade);

            leitor.setDetectando(); // desativa a detecção

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de input, digite apenas números.", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public double lerDados(Leitor leitor) {
        if (!leitor.isDetectando()) {
            return 0.0;
        } else {
            return 1;  // Futuramente será o dado real capturado automaticamente
        }
    }

    @Override
    public void reset() {
        this.altura = 0;
        this.comprimento = 0;
        this.profundidade = 0;
        this.calibrado = false;
    }

    @Override
    public void encerrar() {
        reset();
        setAtivo();
    }

    @Override
    public void calibrar() {
        double velocidadeDoSlider = 0.05; // m/s
        if (getVelocidadeAtualDoSlider() != velocidadeDoSlider) {
            this.velocidadeAtualDoSlider = velocidadeDoSlider;
        }
        this.calibrado = true;
    }

    @Override
    public void enviarDadosAoLeitor(Leitor leitor) {
        // Futuramente será implementado com envio automático de medições contínuas.
        // Atualmente, é enviado diretamente na leitura mockada via iniciar().
    }

    /**
     * Obtém a velocidade atual do slider.
     *
     * @return velocidade em metros por segundo (m/s);
     * Retorna zero caso não esteja calibrado por motivos de simulação, futuramente virá da porta serial.
     */
    @Override
    public double getVelocidadeAtualDoSlider() {
        if (!isCalibrado()) {
            return 0;
        }
        return velocidadeAtualDoSlider;
    }
}
