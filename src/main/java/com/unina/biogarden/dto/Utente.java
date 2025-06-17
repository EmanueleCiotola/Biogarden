package com.unina.biogarden.dto;

public class Utente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String username;

    public Utente(String nome, String cognome, String codiceFiscale, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.username = username;
    }
}
