package com.unina.biogarden.util;

import com.unina.biogarden.dto.Utente;

public class Sessione {
    private static final Sessione instance = new Sessione();
    private Utente utenteCorrente;

    private Sessione() {}
    public static Sessione getInstance() {
        return instance;
    }

    public void setUtenteCorrente(Utente u) {
        this.utenteCorrente = u;
    }

    public Utente getUtenteCorrente() {
        return this.utenteCorrente;
    }

    public void logout() {
        this.utenteCorrente = null;
    }
}

