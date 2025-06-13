package com.unina.biogarden.dao.utente;

import com.unina.biogarden.dto.Utente;

public interface UtenteDao {
    Utente verificaCredenziali(String username, String password);

    Boolean isUsernameLibero(String username);
}
