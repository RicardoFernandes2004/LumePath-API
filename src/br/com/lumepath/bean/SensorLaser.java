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
 * @author Ricardo
 * @version 1.5
 */
public class SensorLaser implements Sensor {

    /** Porta serial utilizada para comunicação com o sensor. Ex.: COM3, /dev/ttyUSB0 */
    private String portaSerial;

    /** Velocidade atual do slider em metros por segundo (m/s). */
    private double velocidadeAtualDoSlider;

    /** Estado de calibração do sensor. */
    private boolean calibrado = false;

    /** Estado de ativação do sensor. Determina se o sensor está operacional. */
    private boolean ativo = false;

    /** Leituras brutas da amostra: altura, comprimento e profundidade. Futuramente trocar por um
     * map*/
    private double altura;
    private double comprimento;
    private double profundidade;
    /**
     * Construtor que define a porta serial utilizada pelo sensor.
     *
     * @param portaSerial a porta serial para comunicação com o hardware.
     */
    public SensorLaser(String portaSerial) {
        setPortaSerial(portaSerial);
    }

    public String getPortaSerial() {
        return portaSerial;
    }

    /**
     * Define a porta serial utilizada pelo sensor para comunicação com o hardware.
     *
     * <p>Este metodo valida o formato da porta serial antes de atribuí-la. São aceitos formatos como:
     * <ul>
     *     <li><code>COM1</code>, <code>COM2</code>, etc. (Windows)</li>
     *     <li><code>/dev/ttyUSB0</code>, <code>/dev/ttyS1</code>, etc. (Unix/Linux)</li>
     * </ul>
     * </p>
     *
     * @param portaSerial a string representando a porta serial.
     */
    public void setPortaSerial(String portaSerial) {
        try {
            if (portaSerial == null || portaSerial.trim().isEmpty()) {
                throw new Exception("Porta serial não pode ser nula ou vazia.");
            }
            if (!(portaSerial.matches("COM[0-9]+") || portaSerial.matches("/dev/tty\\w+"))) {
                throw new Exception("Formato de porta serial inválido: " + portaSerial);
            }
            this.portaSerial = portaSerial;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
            setPortaSerial(JOptionPane.showInputDialog("Digite novamente a porta serial: "));
        }

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

    /**
     * Inicializa o sensor, ativando-o e realizando a leitura dos dados da amostra.
     *
     * <p>Este metodo solicita ao usuário, via {@link JOptionPane}, a entrada dos valores de altura, comprimento e profundidade, que futuramente serão alocados a uma lista, aplicando a fórmula
     * L = v * Δt.
     * Após a leitura, os dados são enviados diretamente ao {@link Leitor} correspondente.</p>
     *
     * <p>O metodo também ativa e desativa o estado de detecção do leitor, simulando um ciclo de leitura.</p>
     *
     * @param leitor o objeto {@link Leitor} que receberá os dados lidos do sensor.
     */
    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        try {
            String inputAltura = JOptionPane.showInputDialog("Digite a altura da amostra:");
            String inputComprimento = JOptionPane.showInputDialog("Digite o comprimento da amostra:");
            String inputProfundidade = JOptionPane.showInputDialog("Digite a profundidade da amostra:");

            if (inputAltura == null || inputComprimento == null || inputProfundidade == null) {
                JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário.", "Cancelado", JOptionPane.WARNING_MESSAGE);
                return;// Evita null pointer
            }

            altura = Double.parseDouble(inputAltura);
            comprimento = Double.parseDouble(inputComprimento);
            profundidade = Double.parseDouble(inputProfundidade);

            leitor.setDetectando(); // ativa a detecção

            leitor.setLeituraAltura(altura);
            leitor.setLeituraComprimento(comprimento);
            leitor.setLeituraProfundidade(profundidade);

            leitor.setDetectando(); // desativa a detecção

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Realiza a leitura do dado mockado do sensor.
     *
     * <p>Retorna um valor fixo simulando a presença ou ausência de detecção.
     * Quando o leitor não está em modo de detecção, retorna <code>0.0</code>;
     * caso contrário, retorna <code>1.0</code>, simulando uma detecção ativa.</p>
     *
     * <p>No futuro, este metodo será implementado para capturar dados reais automaticamente.</p>
     *
     * @param leitor o objeto {@link Leitor} que controla o estado de detecção.
     * @return <code>1.0</code> se estiver detectando; <code>0.0</code> caso contrário.
     */
    @Override
    public double lerDados(Leitor leitor) {
        if (!leitor.isDetectando()) {
            return 0.0;
        } else {
            return 1;  // Futuramente será o dado real capturado automaticamente
        }
    }


    /**
     * Reseta o estado interno do sensor.
     *
     * <p>Zera as leituras de altura, comprimento e profundidade, e desfaz a calibração.</p>
     * <p>Deve ser chamado para preparar o sensor para uma nova medição.</p>
     */
    @Override
    public void reset() {

        this.altura = 0;
        this.comprimento = 0;
        this.profundidade = 0;
        this.calibrado = false;
    }


    /**
     * Encerra a operação do sensor.
     *
     * <p>Executa um reset completo e alterna o estado de ativação.</p>
     * <p>Deve ser chamado após finalizar as leituras para liberar recursos.</p>
     */
    @Override
    public void encerrar() {

        reset();
        setAtivo();
    }


    /**
     * Realiza a calibração do sensor.
     *
     * <p>Define a velocidade padrão do slider utilizada nos cálculos físicos.</p>
     * <p>Evita recalibração se a velocidade já estiver configurada.</p>
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
     * Envia os dados coletados ao {@link Leitor}.
     *
     * <p>Atualmente não implementado, pois os dados são enviados diretamente durante a inicialização mockada.</p>
     * <p>No futuro, será responsável pelo envio automático de medições contínuas.</p>
     *
     * @param leitor o objeto {@link Leitor} que receberá os dados.
     */
    @Override
    public void enviarDadosAoLeitor(Leitor leitor) {

        // implementar quando o professor passar springboot
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
