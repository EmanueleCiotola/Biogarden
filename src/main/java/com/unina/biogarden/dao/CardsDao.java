package com.unina.biogarden.dao;

import java.util.*;

import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.exception.DatabaseException;

public interface CardsDao {
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Progetto> get3ProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Attivita> getAttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Attivita> get3AttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Lotto> get3LottiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
}
