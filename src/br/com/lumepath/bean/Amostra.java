package br.com.lumepath.bean;

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

    private final UUID id = UUID.randomUUID();
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
     * @param localDaColeta local onde a amostra foi coletada.
     * @param tipoDeColeta tipo de coleta realizada.
     * @param localAnatomico localização anatômica da coleta.
     */
    public Amostra(String localDaColeta, String tipoDeColeta, String localAnatomico) {
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

    /**
     * Define o local da coleta.
     *
     * @param localDaColeta local onde a amostra foi coletada.
     * @throws IllegalArgumentException se o valor for nulo ou vazio.
     */
    public void setLocalDaColeta(String localDaColeta) {
        if (localDaColeta == null || localDaColeta.trim().isEmpty()) {
            throw new IllegalArgumentException("Local de coleta não pode ser vazio");
        }
        this.localDaColeta = localDaColeta;
    }

    public String getTipoDeColeta() {
        return tipoDeColeta;
    }

    /**
     * Define o tipo da coleta.
     *
     * @param tipoDeColeta tipo de coleta realizada.
     * @throws IllegalArgumentException se o valor for nulo ou vazio.
     */
    public void setTipoDeColeta(String tipoDeColeta) {
        if (tipoDeColeta == null || tipoDeColeta.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de coleta não pode ser vazio");
        }
        this.tipoDeColeta = tipoDeColeta;
    }

    public String getLocalAnatomico() {
        return localAnatomico;
    }

    /**
     * Define o local anatômico da coleta.
     *
     * @param localAnatomico localização anatômica.
     * @throws IllegalArgumentException se o valor for nulo ou vazio.
     */
    public void setLocalAnatomico(String localAnatomico) {
        if (localAnatomico == null || localAnatomico.trim().isEmpty()) {
            throw new IllegalArgumentException("Local anatômico não pode ser vazio");
        }
        this.localAnatomico = localAnatomico;
    }

    public double getComprimento() {
        return comprimento;
    }

    /**
     * Define o comprimento da amostra.
     *
     * @param comprimento valor a ser definido, deve ser maior ou igual a zero.
     * @throws IllegalArgumentException se o valor for negativo.
     */
    public void setComprimento(double comprimento) {
        if (comprimento >= 0) {
            this.comprimento = comprimento;
        } else {
            throw new IllegalArgumentException("Comprimento deve ser maior ou igual a zero");
        }
    }

    public double getAltura() {
        return altura;
    }

    /**
     * Define a altura da amostra.
     *
     * @param altura valor a ser definido, deve ser maior ou igual a zero.
     * @throws IllegalArgumentException se o valor for negativo.
     */
    public void setAltura(double altura) {
        if (altura >= 0) {
            this.altura = altura;
        } else {
            throw new IllegalArgumentException("Altura deve ser maior ou igual a zero");
        }
    }

    public double getProfundidade() {
        return profundidade;
    }

    /**
     * Define a profundidade da amostra.
     *
     * @param profundidade valor a ser definido, deve ser maior ou igual a zero.
     * @throws IllegalArgumentException se o valor for negativo.
     */
    public void setProfundidade(double profundidade) {
        if (profundidade >= 0) {
            this.profundidade = profundidade;
        } else {
            throw new IllegalArgumentException("Profundidade deve ser maior ou igual a zero");
        }
    }
}
