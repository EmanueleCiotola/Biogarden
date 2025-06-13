package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;

public interface UtenteDao {
    Utente verificaCredenziali(String username, String password);

    boolean isUsernameLibero(String username);

    Utente registraUtente(Utente utente);
}
