package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.exception.DatabaseException;
import com.unina.biogarden.exception.InvalidCredentialsException;

public class UtenteDaoImpl implements UtenteDao {
    @Override
    public Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, DatabaseException {
        //todo: implementare logica di verifica delle credenziali
        if (username.equals("admin") && password.equals("admin")) {
            return  new Utente("admin", "admin", "ADMN12345678ADMN", "Admin_1");
        } else throw new InvalidCredentialsException("Credenziali non valide per l'utente: " + username);
    }

    @Override
    public boolean isUsernameLibero(String username) {
        //todo: implementare logica di verifica dell'esistenza dell'username
        if (!username.equals("admin")) return true;
        return false;
    }

    @Override
    public Utente registraUtente(Utente utente) throws DatabaseException {
        //todo: implementare logica di registrazione dell'utente
        return utente;
    }
}
