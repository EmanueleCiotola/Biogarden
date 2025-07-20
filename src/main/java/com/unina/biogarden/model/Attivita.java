package com.unina.biogarden.model;

import java.time.LocalDate;

public class Attivita {
    private int idAttivita;
    private int idProgetto;
    private String nomeProgetto;
    private String infoColtivatore;
    private String idLotto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String tipo;
    private String stato;

    public Attivita(int idAttivita, int idProgetto, String nomeProgetto, String infoColtivatore, String idLotto, LocalDate dataInizio, LocalDate dataFine, String tipo, String stato) {
        this.idAttivita = idAttivita;
        this.idProgetto = idProgetto;
        this.nomeProgetto = nomeProgetto;
        this.infoColtivatore = infoColtivatore;
        this.idLotto = idLotto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tipo = tipo;
        this.stato = stato;
    }

    public int getIdAttivita() {
        return idAttivita;
    }
    public int getIdProgetto() {
        return idProgetto;
    }
    public String getNomeProgetto() {
        return nomeProgetto;
    }
    public String getInfoColtivatore() {
        return infoColtivatore;
    }
    public String getIdLotto() {
        return idLotto;
    }
    public LocalDate getDataInizio() {
        return dataInizio;
    }
    public LocalDate getDataFine() {
        return dataFine;
    }
    public String getTipo() {
        return tipo;
    }
    public String getStato() {
        return stato;
    }
}
