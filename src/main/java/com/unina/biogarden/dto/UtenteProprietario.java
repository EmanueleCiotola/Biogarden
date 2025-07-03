package com.unina.biogarden.dto;

public class UtenteProprietario extends Utente {
    private String partitaIVA;

    public UtenteProprietario() {}
    public UtenteProprietario(String nome, String cognome, String codiceFiscale, String username, String partitaIVA) {
        super(nome, cognome, codiceFiscale, username);
        this.partitaIVA = partitaIVA;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }
    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }
}
