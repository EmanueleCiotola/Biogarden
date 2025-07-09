package com.unina.biogarden.service;

import com.unina.biogarden.dao.UtenteDao;
import com.unina.biogarden.dao.implementazioneOracle.UtenteDaoImpl;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.util.exception.ValidationException;
import com.unina.biogarden.util.exception.DatabaseException;

public class LoginService {
    private static final LoginService instance = new LoginService();
    private UtenteDao dao = new UtenteDaoImpl();

    private LoginService() {}
    public static LoginService getInstance() {
        return instance;
    }

    public void login(String username, String password) throws InvalidCredentialsException, ValidationException, DatabaseException {
        validaCampiLogin(username, password);
        richiediLogin(username, password);
    }
    private void validaCampiLogin(String username, String password) throws ValidationException {
        if (username.isEmpty()) {
            throw new ValidationException(ErrorMessage.USERNAME_VUOTO);
        } else if (password.isEmpty()) {
            throw new ValidationException(ErrorMessage.PASSWORD_VUOTA);
        }
    }
    private void richiediLogin(String username, String password) throws InvalidCredentialsException, DatabaseException {
        Utente utente = dao.verificaCredenziali(username, password);
        Sessione.getInstance().setUtenteCorrente(utente);
    }

}
