package com.unina.biogarden.model;

import java.time.LocalDate;

public class Progetto {
    private String idProgetto;
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String stato;

    public Progetto(String idProgetto, String nome, LocalDate dataInizio, LocalDate dataFine, String stato) {
        this.idProgetto = idProgetto;
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.stato = stato;
    }

    public String getIdProgetto() {
        return idProgetto;
    }
    public String getNomeProgetto() {
        return nome;
    }
    public LocalDate getDataInizio() {
        return dataInizio;
    }
    public LocalDate getDataFine() {
        return dataFine;
    }
    public String getStato() {
        return stato;
    }
}
