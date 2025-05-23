package br.com.lumepath.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
     *
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

    @Override
    public void iniciar() {
        setAtivo(true);
    }

    @Override
    public double lerDados() {
        if (!detectando) {
            return 0.0;
        } else {
            return 1;
        }
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

    @Override
    public void armazenarDado(Double dado) {

    }

    /**
     * Reseta o sensor: limpa dados, desativa e reativa.
     */
    @Override
    public void reset() {
        dadosProfundidade.clear();
        dadosComprimento.clear();
        dadosAltura.clear();
        calibrado = false;
        setAtivo(false);
        setAtivo(true);
    }

    /**
     * Realiza o cálculo de uma medida com base nos dados armazenados e na velocidade do slider.
     *
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
