package com.unina.biogarden.service;

import java.time.LocalDate;
import java.util.List;

import com.unina.biogarden.dao.TasksDao;
import com.unina.biogarden.dao.implementazionePostgres.TasksDaoImpl;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.NoDataFound;
import com.unina.biogarden.util.exception.ValidationException;

public class AddAndUpdateService {
    private static final AddAndUpdateService instance = new AddAndUpdateService();
    private final TasksDao tasksDao = new TasksDaoImpl();

    private AddAndUpdateService() {}
    public static AddAndUpdateService getInstance() {
        return instance;
    }
    
    public void addNewProject(String name, LocalDate startDate, LocalDate endDate) throws ValidationException, DatabaseException {
        validaCampiNuovoProgetto(name, startDate, endDate);
        String codiceFiscaleProprietario = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();
        tasksDao.addNewProject(codiceFiscaleProprietario, name, startDate, endDate);
    }
    private void validaCampiNuovoProgetto(String name, LocalDate startDate, LocalDate endDate) throws ValidationException {
        if (startDate == null) {
            throw new ValidationException(ErrorMessage.DATA_INIZIO_NULLA);
        } else if (endDate == null) {
            throw new ValidationException(ErrorMessage.DATA_FINE_NULLA);
        } else if (endDate.isBefore(startDate)) {
            throw new ValidationException(ErrorMessage.DATA_FINE_PRECEDE_DATA_INIZIO);
        }
    }

    public List<Progetto> getProgettiAttiviProprietario() throws NoDataFound, DatabaseException {
        List<Progetto> nomiProgetti = tasksDao.getProgettiAttiviProprietario(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());
        if (nomiProgetti.isEmpty()) {
            throw new NoDataFound(ErrorMessage.NESSUN_PROGETTO_TROVATo);
        }

        return nomiProgetti;
    }
    public List<Lotto> getLottiProprietario() throws NoDataFound, DatabaseException {
        List<Lotto> nomiLotti = tasksDao.getLottiByCodiceFiscale(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());
        if (nomiLotti.isEmpty()) {
            throw new NoDataFound(ErrorMessage.NESSUN_LOTTO_TROVATO);
        }

        return nomiLotti;
    }
    public List<UtenteColtivatore> getInfoColtivatoriDisponibili() throws DatabaseException {
        return tasksDao.getInfoColtivatoriDisponibili();
    }

    public List<String> getNomiColtureLotto(String idLotto, String idProgetto) throws NoDataFound, DatabaseException {
        List<String> nomiColture = tasksDao.getNomiColtureLotto(idLotto, idProgetto);
        if (nomiColture.isEmpty()) {
            throw new NoDataFound(ErrorMessage.NESSUNA_COLTURA_TROVATA);
        }

        return nomiColture;
    }

    public void addNewActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, String tipoSemina, String idColtura, String raccoltaQuantitaPrevista) throws ValidationException, DatabaseException {
        validaCampiNuovaAttivita(activityStartDate);
        tasksDao.addNewActivity(idProgetto, idLotto, idColtivatore, tipo, stato, activityStartDate, tipoSemina, idColtura, raccoltaQuantitaPrevista);
    }   
    private void validaCampiNuovaAttivita(LocalDate activityStartDate) throws ValidationException {
        if (activityStartDate == null) {
            throw new ValidationException(ErrorMessage.DATA_INIZIO_NULLA);
        }
    }
    
    public Progetto getProgettoByAttivita(Attivita attivita) throws NoDataFound, DatabaseException {
        Progetto progetto = tasksDao.getProgettoByAttivita(attivita.getIdAttivita());
        if (progetto == null) {
            throw new NoDataFound(ErrorMessage.PROGETTO_NON_TROVATO);
        }
        return progetto;
    }
    public Lotto getLottoByAttivita(Attivita attivita) throws NoDataFound, DatabaseException {
        Lotto lotto = tasksDao.getLottoByAttivita(attivita.getIdAttivita());
        if (lotto == null) {
            throw new NoDataFound(ErrorMessage.LOTTO_NON_TROVATO);
        }
        return lotto;
    }
    public UtenteColtivatore getColtivatoreByAttivita(Attivita attivita) throws NoDataFound, DatabaseException {
        UtenteColtivatore coltivatore = tasksDao.getColtivatoreByAttivita(attivita.getIdAttivita());
        if (coltivatore == null) {
            throw new NoDataFound(ErrorMessage.COLTIVATORE_NON_TROVATO);
        }
        return coltivatore;
    }

    public void updateActivity(Attivita attivita) throws ValidationException, DatabaseException {
        validaCampiModificaAttivita(attivita.getDataInizio(), attivita.getDataFine());
        String codiceFiscaleProprietario = Sessione.getInstance().getUtenteCorrente().getCodiceFiscale();
        tasksDao.updateActivity(attivita, codiceFiscaleProprietario);
    }
    private void validaCampiModificaAttivita(LocalDate activityStartDate, LocalDate activityEndDate) throws ValidationException {
        if (activityStartDate == null) {
            throw new ValidationException(ErrorMessage.DATA_INIZIO_NULLA);
        } else if (activityEndDate != null && activityEndDate.isBefore(activityStartDate)) {
            throw new ValidationException(ErrorMessage.DATA_FINE_PRECEDE_DATA_INIZIO);
        }
    } 
}
