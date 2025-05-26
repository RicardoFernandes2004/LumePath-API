package br.com.lumepath.bean;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * Representa o componente responsável por realizar a leitura de dados do {@link Sensor},
 * associando os resultados a uma {@link Amostra} e calculando a precisão com base na comparação
 * entre sensores de diferentes naturezas (e.g., laser vs. câmera).
 *
 * <p>Gerencia o estado de detecção, armazena as últimas leituras e controla o envio
 * das medições para a amostra.</p>
 * @author Ricardo
 * @version 1.3
 */

public class Leitor implements Leitura {

    /** Sensor associado ao leitor, responsável pela obtenção das medições. */
    private final Sensor sensor;

    /** Amostra associada ao leitor, que receberá os dados processados. */
    private final Amostra amostra;

    /** Precisão calculada com base na diferença entre as leituras do sensor e da câmera. */
    private double precisao;

    /** Marca a data e hora da última leitura realizada pelo sensor. */
    private LocalDateTime ultimaLeitura;

    /** Indica se o sensor está atualmente em modo de detecção. */
    private boolean detectando = false;

    /** Leituras brutas obtidas do sensor: altura, comprimento e profundidade. */
    private double leituraAltura;
    private double leituraComprimento;
    private double leituraProfundidade;

    /** Leituras complementares obtidas via câmera, usadas para verificar a precisão da medição. */
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
    public double getLeituraCameraComprimento() {
        return leituraCameraComprimento;
    }

    public void setLeituraCameraAltura(double leituraCameraAltura) {
        try {
            if (leituraCameraAltura < 0) {
                throw new Exception("Leitura da altura da câmera não pode ser negativa.");
            }
            if (leituraCameraAltura > 500) {
                throw new Exception("Leitura da altura da câmera excede o limite máximo permitido.");
            }
            this.leituraCameraAltura = leituraCameraAltura;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLeituraCameraAltura(Double.parseDouble(JOptionPane.showInputDialog("Digite novamente a altura detectada pela câmera:")));
        }
    }

    public void setLeituraCameraComprimento(double leituraCameraComprimento) {
        try {
            if (leituraCameraComprimento < 0) {
                throw new Exception("Leitura do comprimento da câmera não pode ser negativa.");
            }
            if (leituraCameraComprimento > 500) {
                throw new Exception("Leitura do comprimento da câmera excede o limite máximo permitido.");
            }
            this.leituraCameraComprimento = leituraCameraComprimento;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

            setLeituraCameraComprimento(Double.parseDouble(JOptionPane.showInputDialog("Digite novamente o comprimento detectado pela câmera:")));
        }
    }


    public void setLeituraAltura(double leituraAltura) {
        try {
            if (leituraAltura < 0) {
                throw new Exception("Leitura de altura não pode ser negativa.");
            }
            if (leituraAltura > 500) {
                throw new Exception("Leitura de altura excede o limite máximo permitido.");
            }
            this.leituraAltura = leituraAltura;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLeituraAltura(Double.parseDouble(JOptionPane.showInputDialog("Digite novamente a altura:")));
        }
    }

    public void setLeituraComprimento(double leituraComprimento) {
        try {
            if (leituraComprimento < 0) {
                throw new Exception("Leitura de comprimento não pode ser negativa.");
            }
            if (leituraComprimento > 500) {
                throw new Exception("Leitura de comprimento excede o limite máximo permitido.");
            }
            this.leituraComprimento = leituraComprimento;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLeituraComprimento(Double.parseDouble(JOptionPane.showInputDialog("Digite novamente o comprimento:")));
        }
    }

    public void setLeituraProfundidade(double leituraProfundidade) {
        try {
            if (leituraProfundidade < 0) {
                throw new Exception("Leitura de profundidade não pode ser negativa.");
            }
            if (leituraProfundidade > 500) {
                throw new Exception("Leitura de profundidade excede o limite máximo permitido.");
            }
            this.leituraProfundidade = leituraProfundidade;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLeituraProfundidade(Double.parseDouble(JOptionPane.showInputDialog("Digite novamente a profundidade:")));
        }
    }



    /**
     * Calcula a precisão da medição com base na diferença entre as leituras da câmera
     * e do sensor principal.
     *
     * <p>O cálculo da precisão é a média absoluta entre os erros de altura e comprimento.</p>
     */
    public void calcPrecisao() {
        double erroAltura = Math.abs(leituraCameraAltura - leituraAltura);
        double erroComprimento = Math.abs(leituraCameraComprimento - leituraComprimento);
        this.precisao = (erroAltura + erroComprimento) / 2;
    }

    public double getPrecisao() {
        return precisao;
    }

    /**
     * Realiza o cálculo e atribuição de uma medida específica da amostra.
     *
     * @param tipoDeMedida tipo da medida a ser calculada: "altura", "comprimento" ou "profundidade".
     * @param amostra a {@link Amostra} onde o resultado será armazenado.
     */
    @Override
    public void calcular(String tipoDeMedida, Amostra amostra) {
        try {
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
                    throw new Exception("Tipo inválido: " + tipoDeMedida);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

    }

    /**
     * Executa o ciclo completo de leitura do sensor: inicialização, leitura,
     * envio dos dados para a amostra e encerramento.
     *
     * <p>Ao final do ciclo, atualiza a data/hora da última leitura realizada.</p>
     */
    @Override
    public void lerSensor(){
        sensor.iniciar(this);
        //sensor.enviarDadosAoLeitor(this);
        enviarDadosAmostra(amostra);
        sensor.encerrar();
        setUltimaLeitura();
    }


    /**
     * Envia os dados obtidos pelo leitor para a {@link Amostra}.
     *
     * <p>Se for SensorOpenCV, não faz nada pois a coleta da câmera é apenas para deduzir margem de erro do laser</p>
     *
     * @param amostra a amostra que receberá os dados calculados.
     */
    public void enviarDadosAmostra(Amostra amostra) {
        if (!(sensor instanceof SensorOpenCV)) {
            calcular("altura", amostra);
            calcular("comprimento", amostra);
            calcular("profundidade", amostra);
        }
    }

}
