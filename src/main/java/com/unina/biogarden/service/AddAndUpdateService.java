package com.unina.biogarden.service;

import java.time.LocalDate;
import java.util.List;

import com.unina.biogarden.dao.TasksDao;
import com.unina.biogarden.dao.implementazioneOracle.TasksDaoImpl;
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
        tasksDao.addProject(name, startDate, endDate);
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

    public List<String> getNomiProgettiAttiviProprietario() throws NoDataFound, DatabaseException {
        List<String> nomiProgetti = tasksDao.getNomiProgettiAttiviProprietario(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());
        if (nomiProgetti.isEmpty()) {
            throw new NoDataFound(ErrorMessage.NESSUN_PROGETTO_TROVATo);
        }

        return nomiProgetti;
    }
    public List<String> getNomiLottiProprietario() throws NoDataFound, DatabaseException {
        List<String> nomiLotti = tasksDao.getNomiLottiProprietario(Sessione.getInstance().getUtenteCorrente().getCodiceFiscale());
        if (nomiLotti.isEmpty()) {
            throw new NoDataFound(ErrorMessage.NESSUN_LOTTO_TROVATO);
        }

        return nomiLotti;
    }
    public List<String> getInfoColtivatoriDisponibili() throws DatabaseException {
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
    
    public void updateActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, LocalDate activityEndDate) throws ValidationException, DatabaseException {
        validaCampiModificaAttivita(activityStartDate, activityEndDate);
        tasksDao.updateActivity(idProgetto, idLotto, idColtivatore, tipo, stato, activityStartDate, activityEndDate);
    }
    private void validaCampiModificaAttivita(LocalDate activityStartDate, LocalDate activityEndDate) throws ValidationException {
        if (activityStartDate == null) {
            throw new ValidationException(ErrorMessage.DATA_INIZIO_NULLA);
        } else if (activityEndDate.isBefore(activityStartDate)) {
            throw new ValidationException(ErrorMessage.DATA_FINE_PRECEDE_DATA_INIZIO);
        }
    } 
}
