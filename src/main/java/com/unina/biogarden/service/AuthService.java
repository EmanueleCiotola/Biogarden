package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.util.ErrorMessages;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.util.exception.DatabaseException;

public class AuthService {
    private static final AuthService instance = new AuthService();
    private UtenteDao dao = new UtenteDaoImpl();

    private AuthService() {}
    public static AuthService getInstance() {
        return instance;
    }

    public Utente login(String username, String password) throws InvalidCredentialsException, DatabaseException {
        validaCampiLogin(username, password);
        return richiediLogin(username, password);
    }
    private void validaCampiLogin(String username, String password) throws InvalidCredentialsException {
        if (username.isEmpty()) {
            throw new InvalidCredentialsException(ErrorMessages.USERNAME_VUOTO.getMessage());
        } else if (password.isEmpty()) {
            throw new InvalidCredentialsException(ErrorMessages.PASSWORD_VUOTA.getMessage());
        }
    }
    private Utente richiediLogin(String username, String password) throws InvalidCredentialsException, DatabaseException {
        Utente utente = dao.verificaCredenziali(username, password);
        if (utente != null) Sessione.getInstance().setUtenteCorrente(utente);
        return utente;
    }



    public boolean isPrimoBloccoSignupValido(String nome, String cognome) {
        if (nome.isEmpty()) {
            SnackbarController.show(ErrorMessages.NOME_VUOTO.getMessage());
        } else if (cognome.isEmpty()) {
            SnackbarController.show(ErrorMessages.COGNOME_VUOTO.getMessage());
        } else return true;
        return false;
    }

    public boolean isSecondoBloccoSignupValido(String codiceFiscale, String username) {
        if (!isCodiceFiscaleValido(codiceFiscale)) return false;
        else if (!isUsernameValido(username)) return false;
        return true;
    }
    private boolean isCodiceFiscaleValido(String codiceFiscale) {
        if (codiceFiscale.isEmpty()) {
            SnackbarController.show(ErrorMessages.CODICE_FISCALE_VUOTO.getMessage());
        } else if (codiceFiscale.length() != 16) {
            SnackbarController.show(ErrorMessages.CODICE_FISCALE_LUNGHEZZA.getMessage());
        } else if (!codiceFiscale.matches("^[A-Z0-9]{16}$")) {
            SnackbarController.show(ErrorMessages.CODICE_FISCALE_FORMATO.getMessage());
        } else if (!dao.isCodFiscLibero(codiceFiscale)) {
            SnackbarController.show(ErrorMessages.CODICE_FISCALE_OCCUPATO.getMessage());
        } else return true;
        return false;
    }
    private boolean isUsernameValido(String username) {
        if (username.isEmpty()) {
            SnackbarController.show(ErrorMessages.USERNAME_VUOTO.getMessage());
        } else if (!username.matches("^[\\p{L}0-9._-]+$")) {
            SnackbarController.show(ErrorMessages.USERNAME_FORMATO.getMessage());
        } else if (!dao.isUsernameLibero(username)) {
            SnackbarController.show(ErrorMessages.USERNAME_OCCUPATO.getMessage());
        } else return true;
        return false;
    }

    public boolean isTerzoBloccoSignupValido(String password, String ripetiPassword) {
        if (!isPasswordValida(password)) return false;
        else if (!isRipetizionePasswordValida(password, ripetiPassword)) return false;
        return true;
    }
    public boolean isPasswordValida(String password) {
        if (password.isEmpty()) {
            SnackbarController.show(ErrorMessages.PASSWORD_VUOTA.getMessage());
        } else if (password.length() < 8) {
            SnackbarController.show(ErrorMessages.PASSWORD_LUNGHEZZA.getMessage());
        } else if (password.contains(" ")) {
            SnackbarController.show(ErrorMessages.PASSWORD_SPAZI.getMessage());
        } else if (!password.matches(".*\\p{L}.*")) {
            SnackbarController.show(ErrorMessages.PASSWORD_LETTERA.getMessage());
        } else if (!password.matches(".*\\d.*")) {
            SnackbarController.show(ErrorMessages.PASSWORD_NUMERO.getMessage());
        } else if (!password.matches(".*[\\p{P}\\p{S}].*")) {
            SnackbarController.show(ErrorMessages.PASSWORD_CARATTERE_SPECIALE.getMessage());
        } else return true;
        return false;
    }
    public boolean isRipetizionePasswordValida(String password, String ripetiPassword) {
        if (ripetiPassword.isEmpty()) {
            SnackbarController.show(ErrorMessages.PASSWORD_RIPETUTA_VUOTA.getMessage());
        } else if (!password.equals(ripetiPassword)) {
            SnackbarController.show(ErrorMessages.PASSWORD_NON_COINCIDONO.getMessage());
        } else return true;
        return false;
    }

    public Utente creaUtente(String nome, String cognome, String codiceFiscale, String username) {
        Utente utente = new Utente(nome, cognome, codiceFiscale, username);
        return utente;
    }

    public Utente signup(Utente utenteDaRegistrare) {
        try {
            Utente utente = dao.registraUtente(utenteDaRegistrare);
            if (utente != null) Sessione.getInstance().setUtenteCorrente(utente);
            return utente;
        } catch (DatabaseException e) {
            SnackbarController.show(ErrorMessages.ERRORE_GENERICO.getMessage());
            return null;
        }
    }
}
