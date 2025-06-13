package com.unina.biogarden.dto;

public class Utente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String username;
    private String password;

    public Utente(String nome, String cognome, String codiceFiscale, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.username = username;
        this.password = password;
    }

    public Utente(String nome, String cognome, String codiceFiscale, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.username = username;
    }
}
