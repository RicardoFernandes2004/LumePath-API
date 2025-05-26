package br.com.lumepath.bean;

import java.time.LocalDate;

public class AnalisePatologica {
    private int id;
    private LocalDate dataAnalise;
    private String descricao;
    private String diagnosticoPreliminar;

    private Amostra amostra;  // Associação
    private PatologistaResponsavel patologistaResponsavel;  // Associação

}
