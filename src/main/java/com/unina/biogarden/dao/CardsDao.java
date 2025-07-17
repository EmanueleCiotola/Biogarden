package com.unina.biogarden.dao;

import java.time.LocalDate;
import java.util.*;

import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.exception.DatabaseException;

public interface CardsDao {
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Attivita> getAttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException;

    public void addProject(String name, LocalDate startDate, LocalDate endDate) throws DatabaseException;
    public void addActivity(String name, LocalDate startDate, LocalDate endDate) throws DatabaseException;
}
