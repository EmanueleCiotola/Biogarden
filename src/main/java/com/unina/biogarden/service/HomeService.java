package com.unina.biogarden.service;

import java.util.List;

import com.unina.biogarden.dao.TasksDao;
import com.unina.biogarden.dao.implementazioneOracle.TasksDaoImpl;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.ReportVoceLotto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.DatabaseException;

public class HomeService {
    private static final HomeService instance = new HomeService();
    private final TasksDao tasksDao = new TasksDaoImpl();

    private Sessione sessione;
    private String codFiscProprietario;

    private HomeService() {}
    public static HomeService getInstance() {
        return instance;
    }
    
    public void initialize() {
        sessione = Sessione.getInstance();
        codFiscProprietario = sessione.getUtenteCorrente().getCodiceFiscale();
    }

    public Utente getCurrentUser() throws DatabaseException {
        return Sessione.getInstance().getUtenteCorrente();
    }
    public void logout() throws DatabaseException {
        sessione.logout();
    }

    public List<Progetto> getProgettiUtente() throws DatabaseException {        
        List<Progetto> progetti = tasksDao.getProgettiByCodiceFiscale(codFiscProprietario);

        return progetti;
    }
    public List<Attivita> getAttivitaUtente() throws DatabaseException {
        List<Attivita> attivita = tasksDao.getAttivitaByCodiceFiscale(codFiscProprietario);

        return attivita;
    }
    public List<Lotto> getLottiUtente() throws DatabaseException {
        List<Lotto> lotti = tasksDao.getLottiByCodiceFiscale(codFiscProprietario);

        return lotti;
    }

    public List<ReportVoceLotto> getListaReportLotti() throws DatabaseException {
        List<ReportVoceLotto> vociReport = tasksDao.getReportLotti(codFiscProprietario);

        return vociReport;
    }
}
