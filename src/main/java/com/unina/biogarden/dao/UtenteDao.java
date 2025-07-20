package com.unina.biogarden.dao;

import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.InvalidCredentialsException;
import com.unina.biogarden.util.exception.UtenteColtivatoreException;

public interface UtenteDao {
    Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, UtenteColtivatoreException, DatabaseException;

    boolean isUsernameLibero(String username) throws DatabaseException;
    boolean isCodFiscLibero(String codFisc) throws DatabaseException;

    void registraUtente(Utente utente) throws UtenteColtivatoreException, DatabaseException;
}
