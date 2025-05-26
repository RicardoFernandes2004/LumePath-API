package br.com.lumepath.bean;

import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa uma amostra coletada para análise, contendo informações
 * sobre local, tipo e medidas físicas.
 *
 * <p>Cada amostra possui um identificador único e a data em que foi coletada.</p>
 *
 * <p>As medidas físicas são: comprimento, profundidade e altura.</p>
 *
 * <p>Valores inválidos (nulos, vazios ou negativos) são rejeitados pelas validações nos setters.</p>
 *
 * @author Ricardo
 * @version 1.0
 */
public class Amostra {

    /** Identificador único da amostra */
    private int id;

    /** Data da coleta, definida automaticamente no momento da criação da amostra. */
    private final LocalDate dataDeColeta = LocalDate.now();

    private String localDaColeta;
    private String tipoDeColeta;
    private String localAnatomico;
    private double comprimento;
    private double profundidade;
    private double altura;

    /**
     * Cria uma nova instância de Amostra com as informações básicas.
     *
     * @param id identificador da amostra
     * @param localDaColeta local onde a amostra foi coletada.
     * @param tipoDeColeta tipo de coleta realizada.
     * @param localAnatomico localização anatômica da coleta.
     */
    public Amostra(int id,String localDaColeta, String tipoDeColeta, String localAnatomico) {
        setId(id);
        setLocalDaColeta(localDaColeta);
        setTipoDeColeta(tipoDeColeta);
        setLocalAnatomico(localAnatomico);
    }

    public LocalDate getDataDeColeta() {
        return dataDeColeta;
    }

    public String getLocalDaColeta() {
        return localDaColeta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        try{
            if (id > 0){
                this.id = id;
            }else{
                throw new Exception("ID Invalido, deve ser maior que 0");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Define o local da coleta.
     *
     * @param localDaColeta local onde a amostra foi coletada.
     */
    public void setLocalDaColeta(String localDaColeta){
        try {
            if (localDaColeta == null || localDaColeta.trim().isEmpty()) {
                throw new Exception("Local de coleta não pode ser vazio");
            }
            this.localDaColeta = localDaColeta;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLocalDaColeta(JOptionPane.showInputDialog("Digite novamente o local da coleta: "));
        }
    }

    public String getTipoDeColeta() {
        return tipoDeColeta;
    }

    /**
     * Define o tipo da coleta.
     *
     * @param tipoDeColeta tipo de coleta realizada.
     */
    public void setTipoDeColeta(String tipoDeColeta) {
        try {
            if (tipoDeColeta == null || tipoDeColeta.trim().isEmpty()) {
                throw new Exception("Tipo de coleta não pode ser vazio");
            }
            this.tipoDeColeta = tipoDeColeta;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setTipoDeColeta(JOptionPane.showInputDialog("Digite novamente o tipo de coleta: "));
        }

    }

    public String getLocalAnatomico() {
        return localAnatomico;
    }

    /**
     * Define o local anatômico da coleta.
     *
     * @param localAnatomico localização anatômica.
     */
    public void setLocalAnatomico(String localAnatomico) {
        try {
            if (localAnatomico == null || localAnatomico.trim().isEmpty()) {
            throw new Exception("Local anatômico não pode ser vazio");
        }
            this.localAnatomico = localAnatomico;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setLocalAnatomico(JOptionPane.showInputDialog("Digite novamente o local anatomico: "));
        }

    }

    public double getComprimento() {
        return comprimento;
    }

    /**
     * Define o comprimento da amostra.
     *
     * @param comprimento valor a ser definido, deve ser maior ou igual a zero.
     */
    public void setComprimento(double comprimento) {
        try{
            if (comprimento >= 0) {
                this.comprimento = comprimento;
            } else {
                throw new Exception("Comprimento deve ser maior ou igual a zero");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public double getAltura() {
        return altura;
    }

    /**
     * Define a altura da amostra.
     *
     * @param altura valor a ser definido, deve ser maior ou igual a zero.
     */
    public void setAltura(double altura) {
        try {
            if (altura >= 0) {
                this.altura = altura;
            } else {
                throw new Exception("Altura deve ser maior ou igual a zero");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public double getProfundidade() {
        return profundidade;
    }

    /**
     * Define a profundidade da amostra.
     *
     * @param profundidade valor a ser definido, deve ser maior ou igual a zero.
     */
    public void setProfundidade(double profundidade) {
        try {
            if (profundidade >= 0) {
                this.profundidade = profundidade;
            } else {
                throw new Exception("Profundidade deve ser maior ou igual a zero");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
}
