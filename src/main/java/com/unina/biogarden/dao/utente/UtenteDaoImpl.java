package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;

public class UtenteDaoImpl implements UtenteDao {
    @Override
    public Utente verificaCredenziali(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            return new Utente(username, password);
        }
        return null;
    }
}
