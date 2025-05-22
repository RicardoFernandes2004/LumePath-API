package br.com.lumepath.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * Implementação concreta da interface {@link Sensor} para sensores baseados em tecnologia laser.
 * <p>
 * Capaz de realizar medições em altura, profundidade e comprimento através da detecção contínua
 * de obstáculos via feixe laser.
 * </p>
 */
public class SensorLaser implements Sensor {

    private String portaSerial;
    private double velocidadeAtualDoSlider;
    private boolean detectando = false;
    private double precisao;
    private boolean calibrado = false;
    private boolean ativo = false;
    private LocalDateTime ultimaLeitura;
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

    public LocalDateTime getUltimaLeitura() {
        return ultimaLeitura;
    }

    public void setUltimaLeitura() {
        this.ultimaLeitura = LocalDateTime.now();
    }

    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Ativa ou desativa o sensor. Se não calibrado, realiza calibração automaticamente.
     *
     * @param ativo {@code true} para ativar, {@code false} para desativar.
     */
    public void setAtivo(boolean ativo) {
        if (!isCalibrado()) {
            calibrar();
        }
        this.ativo = ativo;
    }

    public boolean isCalibrado() {
        return calibrado;
    }

    public double getPrecisao() {
        return precisao;
    }

    @Override
    public void iniciar() {
        setAtivo(true);
    }

    @Override
    public double lerDados() {
        return detectando ? 1.0 : 0.0;
    }

    /**
     * Armazena dados lidos continuamente enquanto houver detecção.
     *
     * @param lista lista onde os dados devem ser armazenados.
     */
    @Override
    public void armazenarDado(List<Double> lista) {
        while (detectando) {
            double dado = lerDados();
            if (dado != 0) {
                lista.add(dado);
            }
        }
    }

    /**
     * Reseta o sensor: limpa dados, desativa e reativa após breve intervalo.
     */
    @Override
    public void reset() {
        dadosProfundidade.clear();
        dadosComprimento.clear();
        dadosAltura.clear();
        calibrado = false;
        setAtivo(false);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        setAtivo(true);
    }

    /**
     * Realiza o cálculo de uma medida com base nos dados armazenados e na velocidade do slider.
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
                    amostra.setAltura(getVelocidadeAtualDoSlider() * (dadosAltura.size() - 1));
                } else {
                    amostra.setAltura(0);
                }
                break;
            case "comprimento":
                if (!dadosComprimento.isEmpty()) {
                    amostra.setComprimento(getVelocidadeAtualDoSlider() * (dadosComprimento.size() - 1));
                } else {
                    amostra.setComprimento(0);
                }
                break;
            case "profundidade":
                if (!dadosProfundidade.isEmpty()) {
                    amostra.setProfundidade(getVelocidadeAtualDoSlider() * (dadosProfundidade.size() - 1));
                } else {
                    amostra.setProfundidade(0);
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipoDeMedida);
        }
    }

    @Override
    public void encerrar() {
        setUltimaLeitura();
        setAtivo(false);
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

    /**
     * Obtém a velocidade atual do slider.
     *
     * @return velocidade em metros por segundo (m/s);
     * se não calibrado, retorna valor aleatório para simulação.
     */
    @Override
    public double getVelocidadeAtualDoSlider() {
        if (!isCalibrado()) {
            return RandomGenerator.getDefault().nextDouble();
        }
        return velocidadeAtualDoSlider;
    }
}
