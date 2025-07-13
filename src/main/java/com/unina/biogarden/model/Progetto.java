package com.unina.biogarden.model;

import java.time.LocalDate;

public class Progetto {
    private String nomeProgetto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String stato;

    public Progetto(String nomeProgetto, LocalDate dataInizio, LocalDate dataFine, String stato) {
        this.nomeProgetto = nomeProgetto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.stato = stato;
    }

    public String getNomeProgetto() {
        return nomeProgetto;
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
