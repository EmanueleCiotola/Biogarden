package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.util.ErrorMessages;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.ValidationException;
import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.util.exception.DatabaseException;

public class AuthService {
    private static final AuthService instance = new AuthService();
    private UtenteDao dao = new UtenteDaoImpl();

    private AuthService() {}
    public static AuthService getInstance() {
        return instance;
    }

    public void login(String username, String password) throws ValidationException, InvalidCredentialsException, DatabaseException {
        validaCampiLogin(username, password);
        richiediLogin(username, password);
    }
    private void validaCampiLogin(String username, String password) throws ValidationException {
        if (username.isEmpty()) {
            throw new ValidationException(ErrorMessages.USERNAME_VUOTO.getMessage());
        } else if (password.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_VUOTA.getMessage());
        }
    }
    private void richiediLogin(String username, String password) throws InvalidCredentialsException, DatabaseException {
        Utente utente = dao.verificaCredenziali(username, password);
        Sessione.getInstance().setUtenteCorrente(utente);
    }

    public void validaPrimoBloccoSignup(String nome, String cognome) throws ValidationException {
        if (nome.isEmpty()) {
            throw new ValidationException(ErrorMessages.NOME_VUOTO.getMessage());
        } else if (cognome.isEmpty()) {
            throw new ValidationException(ErrorMessages.COGNOME_VUOTO.getMessage());
        }
    }

    public void validaSecondoBloccoSignup(String codiceFiscale, String username) throws ValidationException, DatabaseException {
        validaCodiceFiscale(codiceFiscale);
        validaUsername(username);
    }
    private void validaCodiceFiscale(String codiceFiscale) throws ValidationException, DatabaseException {
        if (codiceFiscale.isEmpty()) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_VUOTO.getMessage());
        } else if (codiceFiscale.length() != 16) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_LUNGHEZZA.getMessage());
        } else if (!codiceFiscale.matches("^[A-Z0-9]{16}$")) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_FORMATO.getMessage());
        } else if (!dao.isCodFiscLibero(codiceFiscale)) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_OCCUPATO.getMessage());
        }
    }
    private void validaUsername(String username) throws ValidationException, DatabaseException {
        if (username.isEmpty()) {
            throw new ValidationException(ErrorMessages.USERNAME_VUOTO.getMessage());
        } else if (!username.matches("^[\\p{L}0-9._-]+$")) {
            throw new ValidationException(ErrorMessages.USERNAME_FORMATO.getMessage());
        } else if (!dao.isUsernameLibero(username)) {
            throw new ValidationException(ErrorMessages.USERNAME_OCCUPATO.getMessage());
        }
    }

    public void validaTerzoBloccoSignup(String password, String ripetizionePassword) throws ValidationException {
        validaPassword(password);
        validaRipetizionePassword(password, ripetizionePassword);
    }
    public void validaPassword(String password)throws ValidationException {
        if (password.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_VUOTA.getMessage());
        } else if (password.length() < 8) {
            throw new ValidationException(ErrorMessages.PASSWORD_LUNGHEZZA.getMessage());
        } else if (password.contains(" ")) {
            throw new ValidationException(ErrorMessages.PASSWORD_SPAZI.getMessage());
        } else if (!password.matches(".*\\p{L}.*")) {
            throw new ValidationException(ErrorMessages.PASSWORD_LETTERA.getMessage());
        } else if (!password.matches(".*\\d.*")) {
            throw new ValidationException(ErrorMessages.PASSWORD_NUMERO.getMessage());
        } else if (!password.matches(".*[\\p{P}\\p{S}].*")) {
            throw new ValidationException(ErrorMessages.PASSWORD_CARATTERE_SPECIALE.getMessage());
        }
    }
    public void validaRipetizionePassword(String password, String ripetizionePassword) throws ValidationException {
        if (ripetizionePassword.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_RIPETUTA_VUOTA.getMessage());
        } else if (!password.equals(ripetizionePassword)) {
            throw new ValidationException(ErrorMessages.PASSWORD_NON_COINCIDONO.getMessage());
        }
    }

    public void signup(Utente utenteDaRegistrare) throws DatabaseException {
        dao.registraUtente(utenteDaRegistrare);
        Sessione.getInstance().setUtenteCorrente(utenteDaRegistrare);
    }
}
