package com.unina.biogarden.model;

public class Lotto {
    private String idLotto;
    private Float mq;
    private String idProgetto;

    public Lotto(String idLotto, Float mq, String idProgetto) {
        this.idLotto = idLotto;
        this.mq = mq;
        this.idProgetto = idProgetto;
    }

    public String getIdLotto() {
        return idLotto;
    }
    
    public Float getMq() {
        return mq;
    }

    public String getIdProgetto() {
        return idProgetto;
    }
}