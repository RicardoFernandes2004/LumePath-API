package br.com.lumepath.bean;

import br.com.lumepath.utils.ValidaCpf;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Classe que representa um Paciente, contendo informações pessoais como nome, CPF,
 * data de nascimento, sexo, código de prontuário e um identificador único (UUID).
 *
 * <p>A classe também realiza validação de CPF e formatação de data de nascimento.
 * </p>
 *
 * @author Ricardo
 * @version 1.0
 */
public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private String sexo;
    private int codigoProntuario;

    /**
     * Construtor da classe Paciente.
     *
     * @param nome Nome completo do paciente.
     * @param cpf CPF do paciente. Deve ser válido.
     * @param dataDeNascimento Data de nascimento no formato "dd-MM-yyyy".
     * @param sexo Sexo do paciente.
     * @param codigoProntuario Código de prontuário do paciente.
     */
    public Paciente(int id,String nome, String cpf, String dataDeNascimento, String sexo, int codigoProntuario){
        setId(id);
        this.nome = nome;
        setCpf(cpf);
        setDataDeNascimento(dataDeNascimento);
        setSexo(sexo);
        this.codigoProntuario = codigoProntuario;
    }


    public void setId(int id) {
        try{
            if (id > 0){
                this.id = id;
            }else{
                throw new Exception("ID Invalido, deve ser maior que 0");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
            setId(Integer.parseInt(JOptionPane.showInputDialog("Tente novamente mais que 0")));
        }
    }

    public int getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFormattedCpf(){
        return ValidaCpf.imprimeCPF(cpf);
    }

    public int getIdade() {
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(getDataDeNascimento(), hoje);
        return periodo.getYears();
    }

    public String getResumo() {
        return String.format("%s (%s) - %d anos - Código Prontuário: %d",
                getNome(),
                getSexo(),
                getIdade(),
                getCodigoProntuario());
    }

    /**
     * Define o CPF do paciente. O CPF é validado antes de ser atribuído.
     *
     * @param cpf CPF do paciente.
     */
    public void setCpf(String cpf) {
        try {
            if (ValidaCpf.isCPF(cpf)){
                this.cpf = cpf;
            } else {
                throw new Exception("CPF Inválido");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setCpf(JOptionPane.showInputDialog("Tente novamente"));
        }
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    /**
     * Define a data de nascimento do paciente. A data deve ser posterior a 01-01-1900.
     *
     * @param dataDeNascimento Data de nascimento no formato "dd-MM-yyyy".
     */
    public void setDataDeNascimento(String dataDeNascimento) {
        try {
            if (LocalDate.parse(dataDeNascimento, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .isAfter(LocalDate.of(1900, 1, 1))) {
                this.dataDeNascimento = LocalDate.parse(dataDeNascimento, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else {
                throw new Exception("Idade inválida");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            setDataDeNascimento(JOptionPane.showInputDialog("Tente novamente"));
        }
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        try {
            if (sexo == null || sexo.trim().isEmpty()) {
                throw new Exception("Sexo não pode ser nulo ou vazio.");
            }

            sexo = sexo.toUpperCase();

            if (!sexo.equals("M") && !sexo.equals("F")) {
                throw new Exception("Sexo inválido. Use 'M' para masculino ou 'F' para feminino.");
            }

            this.sexo = sexo;

        } catch (Exception e) {
            System.out.println("Erro ao definir sexo: " + e.getMessage());
            setSexo(JOptionPane.showInputDialog("Tente novamente"));
        }
    }

    public int getCodigoProntuario() {
        return codigoProntuario;
    }

    public void setCodigoProntuario(int codigoProntuario) {
        this.codigoProntuario = codigoProntuario;
    }


}