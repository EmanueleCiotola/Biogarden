package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.util.ErrorMessages;

public class LoginService {
    private UtenteDao dao = new UtenteDaoImpl();

    public Utente login(String username, String password) {
        if (username == null || username.isEmpty()) {
            SnackbarController.show(ErrorMessages.USERNAME_VUOTO.getMessage());
            return null;
        } else if (password == null || password.isEmpty()) {
            SnackbarController.show(ErrorMessages.PASSWORD_VUOTA.getMessage());
            return null;
        }

        Utente utente = dao.verificaCredenziali(username, password);
        if (utente != null) {
            return utente;
        } else {
            SnackbarController.show(ErrorMessages.CREDENZIALI_NON_VALIDE.getMessage());
            return null;
        }
    }
}
