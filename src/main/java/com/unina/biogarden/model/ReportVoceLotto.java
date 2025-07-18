package com.unina.biogarden.model;

public class ReportVoceLotto {
    private int idLotto;
    private String tipo;
    private int numeroRaccolteSuccesso;
    private double mediaKg;
    private double minKg;
    private double maxKg;

    public ReportVoceLotto(int idLotto, String tipo, int numero, double media, double min, double max) {
        this.idLotto = idLotto;
        this.tipo = tipo;
        this.numeroRaccolteSuccesso = numero;
        this.mediaKg = media;
        this.minKg = min;
        this.maxKg = max;
    }

    public String getIdLotto() { return String.valueOf(idLotto); }
    public String getTipo() { return String.valueOf(tipo); }
    public int getNumeroRaccolteSuccesso() { return numeroRaccolteSuccesso; }
    public String getMediaKg() { return String.valueOf(mediaKg); }
    public String getMinKg() { return String.valueOf(minKg); }
    public String getMaxKg() { return String.valueOf(maxKg); }
}