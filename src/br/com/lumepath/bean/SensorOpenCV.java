package br.com.lumepath.bean;

import javax.swing.*;

/**
 * Implementação da interface {@link Sensor} para sensores de visão computacional.
 *
 * <p>Atualmente realiza leitura mockada via {@link JOptionPane} para simular
 * os dados obtidos por uma câmera, como altura e largura da amostra.</p>
 *
 * <p>Não armazena estado interno relacionado às medições, enviando os dados diretamente
 * ao {@link Leitor} durante a inicialização.</p>
 * @author Ricardo
 * @version 1.2
 */
public class SensorOpenCV implements Sensor {

    /** Porta serial utilizada para configuração e identificação do sensor. */
    private String portaSerial;

    /** Estado de ativação do sensor. */
    private boolean ativo = false;

    public SensorOpenCV(String portaSerial) {
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

    public void setAtivo() {
        this.ativo = !this.ativo;
    }

    /**
     * Inicializa o sensor, ativando-o e realizando a leitura mockada dos dados da câmera.
     *
     * <p>Os dados lidos são enviados diretamente ao {@link Leitor} para uso posterior
     * no cálculo de precisão.</p>
     *
     * @param leitor o {@link Leitor} que receberá os dados capturados.
     */
    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        lerDados(leitor);
    }


    /**
     * Realiza a leitura mockada dos dados da câmera, solicitando altura e largura ao usuário.
     *
     * <p>Os dados capturados são enviados diretamente ao {@link Leitor} como medidas complementares
     * para aferição da precisão.</p>
     *
     * @param leitor o {@link Leitor} que receberá os dados lidos.
     * @return sempre retorna <code>0</code>, pois o retorno não é utilizado neste contexto.
     */
    @Override
    public double lerDados(Leitor leitor) {
        try {
            double altura = Double.parseDouble(JOptionPane.showInputDialog("Digite a altura detectada pela câmera: "));
            double largura = Double.parseDouble(JOptionPane.showInputDialog("Digite a largura detectada pela câmera: "));

            // Envia direto pro Leitor
            leitor.setLeituraCameraAltura(altura);
            leitor.setLeituraCameraComprimento(largura);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de input, digite apenas números.", JOptionPane.ERROR_MESSAGE);
        }
        return 0; // Retorno não utilizado
    }

    @Override
    public void reset() {
        // Não armazena estado interno, não há nada a resetar
    }

    /**
     * Encerra a operação do sensor, alternando seu estado de ativação.
     */
    @Override
    public void encerrar() {
        setAtivo();
    }

    @Override
    public void calibrar() {
        JOptionPane.showMessageDialog(null, "Câmera calibrada com sucesso!");
    }

    /**
     * Envia os dados ao {@link Leitor}.
     *
     * <p>Atualmente desnecessário, quando puder usar Springboot voltar aqui e refazer.</p>
     *
     * @param leitor o {@link Leitor} que receberia os dados.
     */
    @Override
    public void enviarDadosAoLeitor(Leitor leitor) {
        // Não necessário — dados já enviados diretamente em iniciar()
    }

    /**
     * A câmera fica parada no aparelho, então é constantemente zero.
     */
    @Override
    public double getVelocidadeAtualDoSlider() {
        return 0;
    }
}
