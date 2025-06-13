package com.unina.biogarden.service;

import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.util.ErrorMessages;
import com.unina.biogarden.exception.InvalidCredentialsException;
import com.unina.biogarden.exception.DatabaseException;

public class LoginService {
    private UtenteDao dao = new UtenteDaoImpl();

    public Utente login(String username, String password) {
        if (!areCampiValidi(username, password)) return null;
        return eseguiLogin(username, password);
    }
    private boolean areCampiValidi(String username, String password) {
        if (username.isEmpty()) {
            SnackbarController.show(ErrorMessages.USERNAME_VUOTO.getMessage());
        } else if (password.isEmpty()) {
            SnackbarController.show(ErrorMessages.PASSWORD_VUOTA.getMessage());
        } else return true;
        return false;
    }
    private Utente eseguiLogin(String username, String password) {
        try {
            return dao.verificaCredenziali(username, password);
        } catch (InvalidCredentialsException e) {
            SnackbarController.show(ErrorMessages.CREDENZIALI_NON_VALIDE.getMessage());
        } catch (DatabaseException e) {
            SnackbarController.show(ErrorMessages.ERRORE_GENERICO.getMessage());
        }
        return null;
    }
}