package br.com.lumepath.bean;

import javax.swing.*;

public class PatologistaResponsavel {
    private int id;
    private String nome;
    private String crm;
    private String especialidade;
    private String email;

    /**
     * Construtor
     *
     * @param id id.
     * @param nome nome do patologista.
     * @param crm número de crm.
     * @param especialidade área de atuação.
     * @param email e-mail de contato.
     */
    public PatologistaResponsavel(int id, String nome, String crm, String especialidade, String email) {
        setId(id);
        setNome(nome);
        setCrm(crm);
        setEspecialidade(especialidade);
        setEmail(email);
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }
            this.nome = nome;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
            setNome(JOptionPane.showInputDialog("Digite o nome novamente: "));
        }

    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        try {
            if (crm == null || crm.trim().isEmpty()) {
                throw new Exception("CRM não pode ser vazio.");
            }
            if (!validarCrm(crm)) {
                throw new Exception("Formato de CRM inválido. Exemplo: SP-123456.");
            }
            this.crm = crm;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
            setCrm(JOptionPane.showInputDialog("Digite o CRM novamente: "));
        }

    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            this.especialidade = "Patologia Geral";
        } else {
            this.especialidade = especialidade;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new Exception("E-mail não pode ser vazio.");
            }
            if (!validarEmail(email)) {
                throw new Exception("E-mail inválido.");
            }
            this.email = email;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
            setEmail(JOptionPane.showInputDialog("Digite o e-mail novamente: "));
        }

    }

    /**
     * Retorna um resumo textual do patologista.
     *
     * @return resumo com nome, CRM e especialidade.
     */
    public String getResumo() {
        return String.format("Patologista: %s | CRM: %s | Especialidade: %s", nome, crm, especialidade);
    }

    /**
     * Valida o formato básico do e-mail.
     *
     * @param email e-mail a ser validado.
     * @return true se contiver '@' e '.'.
     */
    private boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Valida o formato do CRM.
     *
     * @param crm CRM a ser validado.
     * @return true se estiver no padrão XX-123456.
     */
    private boolean validarCrm(String crm) {
        return crm.matches("[A-Z]{2}-\\d{4,6}");
    }
}
