package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;

public class UtenteDaoImpl implements UtenteDao {
    @Override
    public Utente verificaCredenziali(String username, String password) {
        //todo: implementare logica di verifica delle credenziali
        if (username.equals("admin") && password.equals("admin")) {
            return new Utente(username, password);
        }
        return null;
    }

    @Override
    public boolean isUsernameLibero(String username) {
        //todo: implementare logica di verifica dell'esistenza dell'username
        if (!username.equals("admin")) return true;
        return false;
    }

    @Override
    public Utente registraUtente(Utente utente) {
        //todo: implementare logica di registrazione dell'utente
        return utente;
    }
}
