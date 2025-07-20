package com.unina.biogarden.service;

import com.unina.biogarden.dao.UtenteDao;
import com.unina.biogarden.dao.implementazionePostgres.UtenteDaoImpl;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.ValidationException;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.UtenteColtivatoreException;

public class SignupService {
    private static final String REGEX_PARTITA_IVA = "\\d+";
    private static final String REGEX_CODICE_FISCALE = "^[A-Z0-9]+$";
    private static final String REGEX_USERNAME = "^[\\p{L}0-9._-]+$";
    private static final String REGEX_PASSWORD_HAS_LETTER = ".*\\p{L}.*";
    private static final String REGEX_PASSWORD_HAS_NUMBER = ".*\\d.*";
    private static final String REGEX_PASSWORD_HAS_SPECIAL = ".*[\\p{P}\\p{S}].*";

    private static final SignupService instance = new SignupService();
    private final UtenteDao dao = new UtenteDaoImpl();

    private SignupService() {}
    public static SignupService getInstance() {
        return instance;
    }

    public void validaBloccoPartitaIvaSignup(String partitaIva) throws ValidationException {
        if (partitaIva.isEmpty()) {
            throw new ValidationException(ErrorMessage.PIVA_VUOTA);
        } else if (!partitaIva.matches(REGEX_PARTITA_IVA)) {
            throw new ValidationException(ErrorMessage.PIVA_FORMATO);
        } else if (partitaIva.length() != 11) {
            throw new ValidationException(ErrorMessage.PIVA_LUNGHEZZA);
        }
    }

    public void validaBloccoNomeCognomeSignup(String nome, String cognome) throws ValidationException {
        if (nome.isEmpty()) {
            throw new ValidationException(ErrorMessage.NOME_VUOTO);
        } else if (cognome.isEmpty()) {
            throw new ValidationException(ErrorMessage.COGNOME_VUOTO);
        }
    }

    public void validaBloccoCodFiscUsernameSignup(String codiceFiscale, String username) throws ValidationException, DatabaseException {
        validaCodiceFiscale(codiceFiscale);
        validaUsername(username);
    }
    private void validaCodiceFiscale(String codiceFiscale) throws ValidationException, DatabaseException {
        if (codiceFiscale.isEmpty()) {
            throw new ValidationException(ErrorMessage.CODICE_FISCALE_VUOTO);
        } else if (!codiceFiscale.matches(REGEX_CODICE_FISCALE)) {
            throw new ValidationException(ErrorMessage.CODICE_FISCALE_FORMATO);
        } else if (codiceFiscale.length() != 16) {
            throw new ValidationException(ErrorMessage.CODICE_FISCALE_LUNGHEZZA);
        } else if (!dao.isCodFiscLibero(codiceFiscale)) {
            throw new ValidationException(ErrorMessage.CODICE_FISCALE_OCCUPATO);
        }
    }
    private void validaUsername(String username) throws ValidationException, DatabaseException {
        if (username.isEmpty()) {
            throw new ValidationException(ErrorMessage.USERNAME_VUOTO);
        } else if (!username.matches(REGEX_USERNAME)) {
            throw new ValidationException(ErrorMessage.USERNAME_FORMATO);
        } else if (!dao.isUsernameLibero(username)) {
            throw new ValidationException(ErrorMessage.USERNAME_OCCUPATO);
        }
    }

    public void validaBloccoPasswordSignup(String password, String ripetizionePassword) throws ValidationException {
        validaPassword(password);
        validaRipetizionePassword(password, ripetizionePassword);
    }
    public void validaPassword(String password)throws ValidationException {
        if (password.isEmpty()) {
            throw new ValidationException(ErrorMessage.PASSWORD_VUOTA);
        } else if (password.contains(" ")) {
            throw new ValidationException(ErrorMessage.PASSWORD_SPAZI);
        } else if (!password.matches(REGEX_PASSWORD_HAS_LETTER)) {
            throw new ValidationException(ErrorMessage.PASSWORD_LETTERA);
        } else if (!password.matches(REGEX_PASSWORD_HAS_NUMBER)) {
            throw new ValidationException(ErrorMessage.PASSWORD_NUMERO);
        } else if (!password.matches(REGEX_PASSWORD_HAS_SPECIAL)) {
            throw new ValidationException(ErrorMessage.PASSWORD_CARATTERE_SPECIALE);
        } else if (password.length() < 8) {
            throw new ValidationException(ErrorMessage.PASSWORD_LUNGHEZZA);
        }
    }
    public void validaRipetizionePassword(String password, String ripetizionePassword) throws ValidationException {
        if (ripetizionePassword.isEmpty()) {
            throw new ValidationException(ErrorMessage.PASSWORD_RIPETUTA_VUOTA);
        } else if (!password.equals(ripetizionePassword)) {
            throw new ValidationException(ErrorMessage.PASSWORD_NON_COINCIDONO);
        }
    }

    public void signup(Utente utenteDaRegistrare) throws UtenteColtivatoreException, DatabaseException {
        dao.registraUtente(utenteDaRegistrare);
        Sessione.getInstance().setUtenteCorrente(utenteDaRegistrare);
    }
}
