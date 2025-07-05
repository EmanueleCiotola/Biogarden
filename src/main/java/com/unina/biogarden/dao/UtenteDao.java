package com.unina.biogarden.dao;

import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.InvalidCredentialsException;

public interface UtenteDao {
    Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, DatabaseException;

    boolean isUsernameLibero(String username);
    boolean isCodFiscLibero(String codFisc);

    void registraUtente(Utente utente) throws DatabaseException;
}
