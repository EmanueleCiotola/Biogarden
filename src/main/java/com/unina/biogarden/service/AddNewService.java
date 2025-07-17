package com.unina.biogarden.service;

import java.time.LocalDate;

import com.unina.biogarden.dao.CardsDao;
import com.unina.biogarden.dao.implementazioneOracle.CardsDaoImpl;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.ValidationException;

public class AddNewService {
    private static final AddNewService instance = new AddNewService();
    private final CardsDao cardsDao = new CardsDaoImpl();

    private AddNewService() {}
    public static AddNewService getInstance() {
        return instance;
    }
    
    public void addNewProject(String name, LocalDate startDate, LocalDate endDate) throws ValidationException, DatabaseException {
        validaCampiNuovoProgetto(name, startDate, endDate);
        cardsDao.addProject(name, startDate, endDate);
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
}
