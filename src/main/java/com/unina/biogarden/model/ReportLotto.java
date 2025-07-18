package com.unina.biogarden.model;

import java.util.List;

public class ReportLotto {
    private String idLotto;
    private List<ReportVoceLotto> voci;
    private int numeroRaccolteSuccesso;

    public ReportLotto(String idLotto, List<ReportVoceLotto> voci, int numeroRaccolteSuccesso) {
        this.idLotto = idLotto;
        this.voci = voci;
        this.numeroRaccolteSuccesso = numeroRaccolteSuccesso;
    }

    public String getIdLotto() { return idLotto; }
    public List<ReportVoceLotto> getVoci() { return voci; }
    public int getNumRaccolte() { return numeroRaccolteSuccesso; }
}