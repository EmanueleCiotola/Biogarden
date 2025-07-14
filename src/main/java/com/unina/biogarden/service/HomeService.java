package com.unina.biogarden.service;

import java.util.List;

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
        
        List<Progetto> progetti = cardsDao.getProgettiByCodiceFiscale(codiceFiscale);

        if (progetti == null || progetti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_PROGETTO_TROVATO);
        }

        return progetti;
    }
    public List<Progetto> get3ProgettiUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();
        
        List<Progetto> progetti = cardsDao.get3ProgettiByCodiceFiscale(codiceFiscale);

        if (progetti == null || progetti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_PROGETTO_TROVATO);
        }

        return progetti;
    }
    public List<Attivita> getAttivitaUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        List<Attivita> attivita = cardsDao.getAttivitaByCodiceFiscale(codiceFiscale);

        if (attivita == null || attivita.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUNA_ATTIVITA_TROVATA);
        }

        return attivita;
    }
    public List<Attivita> get3AttivitaUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        List<Attivita> attivita = cardsDao.get3AttivitaByCodiceFiscale(codiceFiscale);

        if (attivita == null || attivita.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUNA_ATTIVITA_TROVATA);
        }

        return attivita;
    }
    public List<Lotto> getLottiUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        List<Lotto> lotti = cardsDao.getLottiByCodiceFiscale(codiceFiscale);

        if (lotti == null || lotti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_LOTTO_TROVATO);
        }

        return lotti;
    }
    public List<Lotto> get3LottiUtente() throws IllegalSessionException, DatabaseException {
        String codiceFiscale = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();

        List<Lotto> lotti = cardsDao.get3LottiByCodiceFiscale(codiceFiscale);

        if (lotti == null || lotti.isEmpty()) {
            throw new IllegalSessionException(ErrorMessage.NESSUN_LOTTO_TROVATO);
        }

        return lotti;
    }
}
