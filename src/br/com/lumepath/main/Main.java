package br.com.lumepath.main;

import br.com.lumepath.bean.*;

import javax.swing.*;
// AINDA TEM QUE DOCUMENTAR TUDO, E ADICIONAR AS OUTRAS CLASSES

public class Main {
    public static void main(String[] args){
        Sensor sensorLaser = new SensorLaser("COM5");
        Sensor sensorCamera = new SensorOpenCV("COM5");
        Amostra amostra = new Amostra("Hospital xambão", "Oncologia", "Tórax");
        Leitor leitorLaser = new Leitor(sensorLaser, amostra);
        Leitor leitorCamera = new Leitor(sensorCamera, amostra);

        leitorLaser.lerSensor();
        leitorCamera.lerSensor();
        leitorLaser.calcPrecisao();
        JOptionPane.showMessageDialog(null,"Precisão do laser: "+leitorLaser.getPrecisao());

        JOptionPane.showMessageDialog(null, String.format(
                "Data da Coleta: %s\n" +
                        "Local da Coleta: %s\n" +
                        "Tipo de Coleta: %s\n" +
                        "Local Anatômico: %s\n" +
                        "Comprimento: %.2f cm\n" +
                        "Profundidade: %.2f cm\n" +
                        "Altura: %.2f cm",
                amostra.getDataDeColeta(),
                amostra.getLocalDaColeta(),
                amostra.getTipoDeColeta(),
                amostra.getLocalAnatomico(),
                amostra.getComprimento(),
                amostra.getProfundidade(),
                amostra.getAltura()

        ));

    }
}
