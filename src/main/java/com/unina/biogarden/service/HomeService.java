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

    private HomeService() {}
    public static HomeService getInstance() {
        return instance;
    }

    public Utente getCurrentUser() throws DatabaseException {
        return Sessione.getInstance().getUtenteCorrente();
    }
    public void logout() throws DatabaseException {
        Sessione.getInstance().logout();
    }

    public List<Progetto> getProgettiUtente() throws DatabaseException {        
        List<Progetto> progetti = tasksDao.getProgettiByCodiceFiscale(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());

        return progetti;
    }
    public List<Attivita> getAttivitaUtente() throws DatabaseException {
        List<Attivita> attivita = tasksDao.getAttivitaByCodiceFiscale(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());

        return attivita;
    }
    public List<Lotto> getLottiUtente() throws DatabaseException {
        List<Lotto> lotti = tasksDao.getLottiByCodiceFiscale(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());

        return lotti;
    }

    public List<ReportVoceLotto> getListaReportLotti() throws DatabaseException {
        List<ReportVoceLotto> vociReport = tasksDao.getReportLotti(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());

        return vociReport;
    }
}
