package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.exception.DatabaseException;
import com.unina.biogarden.exception.InvalidCredentialsException;

public interface UtenteDao {
    Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, DatabaseException;

    boolean isUsernameLibero(String username);

    Utente registraUtente(Utente utente) throws DatabaseException;
}
