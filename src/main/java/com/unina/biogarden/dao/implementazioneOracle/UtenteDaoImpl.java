package com.unina.biogarden.dao.implementazioneOracle;

import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.dao.UtenteDao;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.model.UtenteProprietario;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.exception.DatabaseException;

public class UtenteDaoImpl implements UtenteDao {
    @Override
    public Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, DatabaseException {
        //todo: implementare logica di verifica delle credenziali
        if (username.equals("admin") && password.equals("admin")) {
            return  new UtenteProprietario("admin", "admin", "ADMN12345678ADMN", "Admin_1", "12345678910");
        } else throw new InvalidCredentialsException(ErrorMessage.CREDENZIALI_NON_VALIDE);
    }

    @Override
    public boolean isUsernameLibero(String username) {
        //todo: implementare logica di verifica dell'esistenza dell'username
        if (!username.equals("admin")) return true;
        return false;
    }
    @Override
    public boolean isCodFiscLibero(String codFisc) {
        //todo: implementare logica di verifica dell'esistenza del codice fiscale
        if (!codFisc.equals("1234abcd1234abcd")) return true;
        return false;
    }

    @Override
    public void registraUtente(Utente utente) throws DatabaseException {
        //todo: implementare logica di registrazione dell'utente
    }
}
