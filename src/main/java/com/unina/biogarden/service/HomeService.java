package com.unina.biogarden.service;

import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.Sessione;

public class HomeService {
    private static final HomeService instance = new HomeService();

    private HomeService() {}
    public static HomeService getInstance() {
        return instance;
    }

    public Utente getCurrentUser() {
        return Sessione.getInstance().getUtenteCorrente();
    }

    public void logout() {
        Sessione.getInstance().logout();
    }
}
