package br.com.lumepath.main;

import br.com.lumepath.bean.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        PatologistaResponsavel patologista = null;

        int respPatologista = JOptionPane.showConfirmDialog(null, "Você é um patologista?", "Identificação", JOptionPane.YES_NO_OPTION);

        if (respPatologista == JOptionPane.YES_OPTION) {
            // Cadastro do patologista
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Patologista:"));
            String nome = JOptionPane.showInputDialog("Nome:");
            String crm = JOptionPane.showInputDialog("CRM:");
            String especialidade = JOptionPane.showInputDialog("Especialidade:");
            String email = JOptionPane.showInputDialog("Email:");

            patologista = new PatologistaResponsavel(id, nome, crm, especialidade, email);
        } else {
            JOptionPane.showMessageDialog(null, "Encerrando o sistema.");
            System.exit(0);
        }

        while (true) {
            int opcao = JOptionPane.showConfirmDialog(null, "Deseja iniciar uma nova análise?", "Menu", JOptionPane.YES_NO_OPTION);

            if (opcao != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Programa encerrado.");
                break;
            }

            // Cadastro de paciente
            int idPaciente = Integer.parseInt(JOptionPane.showInputDialog("ID do Paciente:"));
            String nomePaciente = JOptionPane.showInputDialog("Nome do Paciente:");
            String cpf = JOptionPane.showInputDialog("CPF:");
            String dataNascimento = JOptionPane.showInputDialog("Data de Nascimento (dd-MM-yyyy):");
            String sexo = JOptionPane.showInputDialog("Sexo (M/F):");
            int codProntuario = Integer.parseInt(JOptionPane.showInputDialog("Código do Prontuário:"));

            Paciente paciente = new Paciente(idPaciente, nomePaciente, cpf, dataNascimento, sexo, codProntuario);

            // Cadastro de amostra
            int idAmostra = Integer.parseInt(JOptionPane.showInputDialog("ID da Amostra:"));
            String localColeta = JOptionPane.showInputDialog("Local da Coleta:");
            String tipoColeta = JOptionPane.showInputDialog("Tipo da Coleta:");
            String localAnatomico = JOptionPane.showInputDialog("Local Anatômico:");

            Amostra amostra = new Amostra(idAmostra, localColeta, tipoColeta, localAnatomico);

            // Sensores e Leitores
            ISensor sensorLaser = new SensorLaser("COM5");
            ISensor sensorCamera = new SensorOpenCV("COM5");

            Leitor leitorLaser = new Leitor(sensorLaser, amostra);
            Leitor leitorCamera = new Leitor(sensorCamera, amostra);

            // Leitura
            leitorLaser.lerSensor();
            leitorCamera.lerSensor();

            leitorLaser.calcPrecisao();

            JOptionPane.showMessageDialog(null, "Precisão do laser: " + leitorLaser.getPrecisao());

            // Cadastro da análise patológica
            int idAnalise = Integer.parseInt(JOptionPane.showInputDialog("ID da Análise:"));
            String descricao = JOptionPane.showInputDialog("Descrição da Análise:");
            String diagnostico = JOptionPane.showInputDialog("Diagnóstico Preliminar:");

            AnalisePatologica analise = new AnalisePatologica(idAnalise, descricao, diagnostico);
            analise.setAmostra(amostra);
            analise.setPatologista(patologista);

            // Emissão do laudo
            String laudo = analise.emitirLaudo();
            JOptionPane.showMessageDialog(null, laudo);
        }
    }
}
