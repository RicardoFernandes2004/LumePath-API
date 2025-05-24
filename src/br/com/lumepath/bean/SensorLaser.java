package br.com.lumepath.bean;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface {@link Sensor} para sensores baseados em tecnologia laser.
 * <p>
 * Capaz de realizar medições em altura, profundidade e comprimento através da detecção contínua
 * de obstáculos via feixe laser.
 * </p>
 */
public class SensorLaser implements Sensor {

    private String portaSerial;
    private double velocidadeAtualDoSlider;
    private boolean calibrado = false;
    private boolean ativo = false;
    /**
     * O uso do ArrayList é essencial para armazenar as diversas medições realizadas pelo   sensor durante o processo de detecção.
     * Como o sensor realiza medições contínuas e dinâmicas ao longo do tempo, a quantidade de dados não é conhecida previamente, variando conforme o contexto de uso.
     * O array list também conta com o metodo size, que é crucial para tornar o calculo do tamanho da amostra dinâmico, entrando como o Δd na fórmula S = v * Δd.
     * */
    private final List<Double> dadosAltura = new ArrayList<>();
    private final List<Double> dadosProfundidade = new ArrayList<>();
    private final List<Double> dadosComprimento = new ArrayList<>();

    /**
     * Construtor que define a porta serial utilizada pelo sensor.
     *
     * @param portaSerial a porta serial para comunicação com o hardware.
     * @throws IllegalArgumentException se a porta for nula, vazia ou não estiver no formato esperado.
     */
    public SensorLaser(String portaSerial) {
        setPortaSerial(portaSerial);
    }

    public List<Double> getDadosComprimento() {
        return dadosComprimento;
    }

    public List<Double> getDadosAltura() {
        return dadosAltura;
    }

    public List<Double> getDadosProfundidade() {
        return dadosProfundidade;
    }

    public String getPortaSerial() {
        return portaSerial;
    }

    /**
     * Define a porta serial utilizada pelo sensor.
     * O metodo isEmpty() é utilizado para verificar se a string representando a porta serial é vazia.
     *
     * @param portaSerial a nova porta serial.
     * @throws IllegalArgumentException se a porta for nula, vazia ou estiver em formato inválido.
     */
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
     *
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
    /**
     * Armazena dados lidos continuamente enquanto houver detecção.
     *
     * @param lista lista onde os dados devem ser armazenados.
     */

    public void armazenarDado(List<Double> lista, Leitor leitor) {
        while (leitor.isDetectando()) {
            double dado = lerDados(leitor);
            if (dado != 0) {
                lista.add(dado);
            }
        }
    }

    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        try {
            int alturaTesteAmostra=Integer.parseInt(JOptionPane.showInputDialog("Digite o altura da amostra: "));
            leitor.setDetectando();
            for (int i = 0; i < alturaTesteAmostra; i++) {
                armazenarDado(dadosAltura,leitor);
            }
            leitor.setDetectando();

            int comprimentoTesteAmostra = Integer.parseInt(JOptionPane.showInputDialog("Digite o comprimento da amostra: "));
            leitor.setDetectando();
            for (int i = 0; i < comprimentoTesteAmostra; i++) {
                armazenarDado(dadosComprimento,leitor);
            }
            leitor.setDetectando();

            int profundidadeTesteAmostra = Integer.parseInt(JOptionPane.showInputDialog("Digite a profundidade da amostra: "));
            leitor.setDetectando();
            for (int i = 0; i < profundidadeTesteAmostra; i++) {
                armazenarDado(dadosProfundidade,leitor);
            }
            leitor.setDetectando();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de input, digite apenas números.", JOptionPane.ERROR_MESSAGE);
        }


    }

    @Override
    public double lerDados(Leitor leitor) {
        if (!leitor.isDetectando()) {
            return 0.0;
        } else {
            return 1;
        }
    }



    /**
     * Reseta o sensor: limpa dados para desativação segura
     */
    @Override
    public void reset() {
        this.dadosProfundidade.clear();
        this.dadosComprimento.clear();
        this.dadosAltura.clear();
        this.calibrado = false;
    }


    @Override
    public void encerrar() {
        reset();
        setAtivo();
    }

    /**
     * Realiza a calibração, ajustando a velocidade padrão do slider.
     */
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
        leitor.addDadosAltura((double)(this.getDadosAltura().size()-1));
        leitor.addDadosProfundidade((double)this.getDadosProfundidade().size()-1);
        leitor.addDadosComprimento((double)this.getDadosComprimento().size()-1);
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
