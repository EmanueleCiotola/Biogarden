package com.unina.biogarden.service;

import com.unina.biogarden.dao.UtenteDao;
import com.unina.biogarden.dao.implementazioneOracle.UtenteDaoImpl;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.ErrorMessages;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.ValidationException;
import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.util.exception.DatabaseException;

public class AuthService {
    private static final String REGEX_PARTITA_IVA = "\\d+";
    private static final String REGEX_CODICE_FISCALE = "^[A-Z0-9]+$";
    private static final String REGEX_USERNAME = "^[\\p{L}0-9._-]+$";
    private static final String REGEX_PASSWORD_HAS_LETTER = ".*\\p{L}.*";
    private static final String REGEX_PASSWORD_HAS_NUMBER = ".*\\d.*";
    private static final String REGEX_PASSWORD_HAS_SPECIAL = ".*[\\p{P}\\p{S}].*";

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
            throw new ValidationException(ErrorMessages.USERNAME_VUOTO);
        } else if (password.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_VUOTA);
        }
    }
    private void richiediLogin(String username, String password) throws InvalidCredentialsException, DatabaseException {
        Utente utente = dao.verificaCredenziali(username, password);
        Sessione.getInstance().setUtenteCorrente(utente);
    }

    public void validaBloccoPartitaIvaSignup(String partitaIva) throws ValidationException {
        if (partitaIva.isEmpty()) {
            throw new ValidationException(ErrorMessages.PIVA_VUOTA);
        } else if (!partitaIva.matches(REGEX_PARTITA_IVA)) {
            throw new ValidationException(ErrorMessages.PIVA_FORMATO);
        } else if (partitaIva.length() != 11) {
            throw new ValidationException(ErrorMessages.PIVA_LUNGHEZZA);
        }
    }

    public void validaBloccoNomeCognomeSignup(String nome, String cognome) throws ValidationException {
        if (nome.isEmpty()) {
            throw new ValidationException(ErrorMessages.NOME_VUOTO);
        } else if (cognome.isEmpty()) {
            throw new ValidationException(ErrorMessages.COGNOME_VUOTO);
        }
    }

    public void validaBloccoCodFiscUsernameSignup(String codiceFiscale, String username) throws ValidationException, DatabaseException {
        validaCodiceFiscale(codiceFiscale);
        validaUsername(username);
    }
    private void validaCodiceFiscale(String codiceFiscale) throws ValidationException, DatabaseException {
        if (codiceFiscale.isEmpty()) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_VUOTO);
        } else if (!codiceFiscale.matches(REGEX_CODICE_FISCALE)) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_FORMATO);
        } else if (codiceFiscale.length() != 16) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_LUNGHEZZA);
        } else if (!dao.isCodFiscLibero(codiceFiscale)) {
            throw new ValidationException(ErrorMessages.CODICE_FISCALE_OCCUPATO);
        }
    }
    private void validaUsername(String username) throws ValidationException, DatabaseException {
        if (username.isEmpty()) {
            throw new ValidationException(ErrorMessages.USERNAME_VUOTO);
        } else if (!username.matches(REGEX_USERNAME)) {
            throw new ValidationException(ErrorMessages.USERNAME_FORMATO);
        } else if (!dao.isUsernameLibero(username)) {
            throw new ValidationException(ErrorMessages.USERNAME_OCCUPATO);
        }
    }

    public void validaBloccoPasswordSignup(String password, String ripetizionePassword) throws ValidationException {
        validaPassword(password);
        validaRipetizionePassword(password, ripetizionePassword);
    }
    public void validaPassword(String password)throws ValidationException {
        if (password.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_VUOTA);
        } else if (password.contains(" ")) {
            throw new ValidationException(ErrorMessages.PASSWORD_SPAZI);
        } else if (!password.matches(REGEX_PASSWORD_HAS_LETTER)) {
            throw new ValidationException(ErrorMessages.PASSWORD_LETTERA);
        } else if (!password.matches(REGEX_PASSWORD_HAS_NUMBER)) {
            throw new ValidationException(ErrorMessages.PASSWORD_NUMERO);
        } else if (!password.matches(REGEX_PASSWORD_HAS_SPECIAL)) {
            throw new ValidationException(ErrorMessages.PASSWORD_CARATTERE_SPECIALE);
        } else if (password.length() < 8) {
            throw new ValidationException(ErrorMessages.PASSWORD_LUNGHEZZA);
        }
    }
    public void validaRipetizionePassword(String password, String ripetizionePassword) throws ValidationException {
        if (ripetizionePassword.isEmpty()) {
            throw new ValidationException(ErrorMessages.PASSWORD_RIPETUTA_VUOTA);
        } else if (!password.equals(ripetizionePassword)) {
            throw new ValidationException(ErrorMessages.PASSWORD_NON_COINCIDONO);
        }
    }

    public void signup(Utente utenteDaRegistrare) throws DatabaseException {
        dao.registraUtente(utenteDaRegistrare);
        Sessione.getInstance().setUtenteCorrente(utenteDaRegistrare);
    }
}
