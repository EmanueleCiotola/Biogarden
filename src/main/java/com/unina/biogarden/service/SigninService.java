package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.router.Router;

public class SigninService {
    private UtenteDao dao = new UtenteDaoImpl();

    public Utente signin(String username, String password) {
        if (username.isEmpty()) {
            SnackbarController.show("Devi inserire un'email per continuare.");
        } else if (password.isEmpty()) {
            SnackbarController.show("Devi inserire una password per continuare.");
        } else if (dao.verificaCredenziali(username, password) != null) {
            Router.getInstance().navigateTo("homePage");
        } else {
            SnackbarController.show("Credenziali non valide.");
        }
        return null;
    }
}
