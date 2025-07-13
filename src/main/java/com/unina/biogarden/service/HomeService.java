package com.unina.biogarden.service;

import java.util.*;

import com.unina.biogarden.dao.CardsDao;
import com.unina.biogarden.dao.implementazioneOracle.CardsDaoImpl;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.IllegalSessionException;
import com.unina.biogarden.util.exception.DatabaseException;

public class HomeService {
    private static final HomeService instance = new HomeService();
    private final CardsDao cardsDao = new CardsDaoImpl();

    private HomeService() {}
    public static HomeService getInstance() {
        return instance;
    }
    public Utente getCurrentUser() {
        return Sessione.getInstance().getUtenteCorrente();
    }
    public void logout() {
        Sessione.getInstance().logout();
    }

    public List<Progetto> getProgettiUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();
        
        ArrayList<Progetto> progetti = cardsDao.getProgettiByCodiceFiscale(codiceFiscale);

        if (progetti == null || progetti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_PROGETTO_TROVATO);
        }

        return progetti;
    }
    public List<Attivita> getAttivitaUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        ArrayList<Attivita> attivita = cardsDao.getAttivitaByCodiceFiscale(codiceFiscale);

        if (attivita == null || attivita.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUNA_ATTIVITA_TROVATA);
        }

        return attivita;
    }
    public ArrayList<Lotto> getLottiUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        ArrayList<Lotto> lotti = cardsDao.getLottiByCodiceFiscale(codiceFiscale);

        if (lotti == null || lotti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_LOTTO_TROVATO);
        }

        return lotti;
    }
}
