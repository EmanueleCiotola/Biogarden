package com.unina.biogarden.dto;

public class UtenteColtivatore extends Utente {
    private double saldo;

    public UtenteColtivatore(String nome, String cognome, String codiceFiscale, String username, double saldo) {
        super(nome, cognome, codiceFiscale, username);
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
