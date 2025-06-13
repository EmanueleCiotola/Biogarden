package com.unina.biogarden.util;

import com.unina.biogarden.dto.Utente;

public class Sessione {
    private static Utente utenteCorrente;

    public static void setUtenteCorrente(Utente u) {
        utenteCorrente = u;
    }

    public static Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    public static void logout() {
        utenteCorrente = null;
    }
}

