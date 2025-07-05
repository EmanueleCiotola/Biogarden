package com.unina.biogarden.model;

public class UtenteProprietario extends Utente {
    private String partitaIva;

    public UtenteProprietario() {}
    public UtenteProprietario(String nome, String cognome, String codiceFiscale, String username, String partitaIva) {
        super(nome, cognome, codiceFiscale, username);
        this.partitaIva = partitaIva;
    }

    public String getPartitaIva() {
        return partitaIva;
    }
    public void setPartitaIva(String partitaIVA) {
        this.partitaIva = partitaIVA;
    }
}
