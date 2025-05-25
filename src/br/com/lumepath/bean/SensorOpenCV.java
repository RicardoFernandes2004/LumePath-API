package br.com.lumepath.bean;

import javax.swing.*;

public class SensorOpenCV implements Sensor {

    private String portaSerial;
    private boolean ativo = false;

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

    @Override
    public void iniciar(Leitor leitor) {
        setAtivo();
        lerDados(leitor);
    }

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

    @Override
    public void encerrar() {
        setAtivo();
    }

    @Override
    public void calibrar() {
        JOptionPane.showMessageDialog(null, "Câmera calibrada com sucesso!");
    }

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
