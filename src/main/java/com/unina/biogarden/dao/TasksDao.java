package com.unina.biogarden.dao;

import java.time.LocalDate;
import java.util.*;

import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.ReportVoceLotto;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.exception.DatabaseException;

public interface TasksDao {
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Attivita> getAttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException;
    
    public Map<String, Set<String>> getRelazioniProgettoLotto(String codiceFiscale) throws DatabaseException;
    
    public void addNewProject(String codFisc, String name, LocalDate startDate, LocalDate endDate) throws DatabaseException;
    public void addNewActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, String tipoSemina, String idColtura, String raccoltaQuantitaPrevista) throws DatabaseException;
    
    public ArrayList<Progetto> getProgettiAttiviProprietario(String codiceFiscale) throws DatabaseException;
    public List<UtenteColtivatore> getInfoColtivatoriDisponibili() throws DatabaseException;
    public List<String> getNomiColtureLotto(String idLotto, String idProgetto) throws DatabaseException;

    public List<ReportVoceLotto> getReportLotti(String codiceFiscale) throws DatabaseException;

    public Progetto getProgettoByAttivita(int idAttivita) throws DatabaseException;
    public Lotto getLottoByAttivita(int idAttivita) throws DatabaseException;
    public UtenteColtivatore getColtivatoreByAttivita(int idAttivita) throws DatabaseException;

    public void updateActivity(String idProgetto, String idLotto, String idColtivatore, String codFisc, String tipo, String stato, LocalDate activityStartDate, LocalDate activityEndDate) throws DatabaseException;
}
