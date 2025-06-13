package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.router.Router;

public class LoginService {
    private UtenteDao dao = new UtenteDaoImpl();

    public Utente login(String username, String password) {
        if (username.isEmpty()) {
            SnackbarController.show("Devi inserire un'email per continuare.");
            return null;
        } else if (password.isEmpty()) {
            SnackbarController.show("Devi inserire una password per continuare.");
            return null;
        }

        Utente utente = dao.verificaCredenziali(username, password);
        if (utente != null) {
            Router.getInstance().navigateTo("homePage");
            return utente;
        } else {
            SnackbarController.show("Credenziali non valide.");
            return null;
        }
    }
}
