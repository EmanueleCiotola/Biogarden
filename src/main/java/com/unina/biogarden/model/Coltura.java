package com.unina.biogarden.model;

public class Coltura {
    private int idColtura;
    private int idLotto;
    private int idProgetto;
    private String tipo;

    public Coltura(int idColtura, int idLotto, int idProgetto, String tipo) {
        this.idColtura = idColtura;
        this.idLotto = idLotto;
        this.idProgetto = idProgetto;
        this.tipo = tipo;
    }

    public int getIdColtura() {
        return idColtura;
    }
    
    public int getIdLotto() {
        return idLotto;
    }

    public int getIdProgetto() {
        return idProgetto;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Coltura " + idColtura + ": " + tipo;
    }
}