package com.unina.biogarden.dto;

public abstract class Utente {
    private String nome;
    private String cognome;
    private String codFisc;
    private String username;

    public Utente() {}
    public Utente(String nome, String cognome, String codiceFiscale, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFisc = codiceFiscale;
        this.username = username;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getCodFisc() {
        return codFisc;
    }
    public void setCodFisc(String codiceFiscale) {
        this.codFisc = codiceFiscale;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}