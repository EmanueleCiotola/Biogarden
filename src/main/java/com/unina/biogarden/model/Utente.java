package com.unina.biogarden.model;

public abstract class Utente {
    private String nome;
    private String cognome;
    private String codFisc;
    private String username;
    private String password;

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
    
    public String getCodiceFiscale() {
        return codFisc;
    }
    public void setCodiceFiscale(String codiceFiscale) {
        this.codFisc = codiceFiscale;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}