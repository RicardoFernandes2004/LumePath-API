package br.com.lumepath.bean;

import javax.swing.*;
import java.time.LocalDate;

public class AnalisePatologica {

    private int id;
    private final LocalDate dataAnalise = LocalDate.now();
    private String descricao;
    private String diagnosticoPreliminar;
    private Amostra amostra;
    private PatologistaResponsavel patologistaResponsavel;

    /**
     * Construtor completo da análise patológica.
     *
     * @param id identificador único.
     * @param descricao descrição detalhada.
     * @param diagnosticoPreliminar hipótese diagnóstica.
     */
    public AnalisePatologica(int id, String descricao, String diagnosticoPreliminar) {
        setId(id);
        setDescricao(descricao);
        setDiagnosticoPreliminar(diagnosticoPreliminar);
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        try {
            if (id <= 0) {
                throw new Exception("ID deve ser maior que zero.");
            }
            this.id = id;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setId(Integer.parseInt(JOptionPane.showInputDialog("Digite o ID novamente:")));
        }

    }

    public LocalDate getDataAnalise() {
        return dataAnalise;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        try {
            if (descricao == null || descricao.trim().isEmpty()) {
                throw new Exception("Descrição não pode ser vazia.");
            }
            this.descricao = descricao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setDescricao(JOptionPane.showInputDialog("Digite a descrição novamente: "));
        }

    }

    public String getDiagnosticoPreliminar() {
        return diagnosticoPreliminar;
    }

    public void setDiagnosticoPreliminar(String diagnosticoPreliminar) {
        try {
            if (diagnosticoPreliminar == null || diagnosticoPreliminar.trim().isEmpty()) {
                throw new Exception("Diagnóstico preliminar não pode ser vazio.");
            }
            this.diagnosticoPreliminar = diagnosticoPreliminar;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setDiagnosticoPreliminar(JOptionPane.showInputDialog("Digite o diagnostico preliminar novamente: "));
        }

    }

    public Amostra getAmostra() {
        return amostra;
    }

    public void setAmostra(Amostra amostra) {
        try {
            if (amostra == null) {
                throw new IllegalArgumentException("Amostra não pode ser nula.");
            }
            this.amostra = amostra;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public PatologistaResponsavel getPatologistaResponsavel() {
        return patologistaResponsavel;
    }

    public void setPatologista(PatologistaResponsavel patologistaResponsavel) {
        try {
            if (patologistaResponsavel == null) {
                throw new Exception("Patologista responsável não pode ser nulo.");
            }
            this.patologistaResponsavel = patologistaResponsavel;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Gera um laudo da análise patológica.
     *
     * @return laudo formatado como String.
     */
    public String emitirLaudo() {
        if (amostra == null || patologistaResponsavel == null) {
            return "Dados incompletos: Amostra ou Patologista não associados.";
        }

        return String.format(
                "==== Laudo de Análise Patológica ====\n" +
                        "ID: %d\n" +
                        "Data da Análise: %s\n" +
                        "Descrição: %s\n" +
                        "Diagnóstico Preliminar: %s\n" +
                        "Amostra: %s\n" +
                        "Patologista: %s\n",
                id, dataAnalise, descricao, diagnosticoPreliminar,
                amostra.getLocalAnatomico(), patologistaResponsavel.getResumo()
        );
    }
}
