package com.unina.biogarden.model;

public class Lotto {
    private String idLotto;
    private Float mq;

    public Lotto(String idLotto, Float mq) {
        this.idLotto = idLotto;
        this.mq = mq;
    }

    public String getIdLotto() {
        return idLotto;
    }
    
    public Float getMq() {
        return mq;
    }

    @Override
    public String toString() {
        return "Lotto numero " + idLotto;
    }
}