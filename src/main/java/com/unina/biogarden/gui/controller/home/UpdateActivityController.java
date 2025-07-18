package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class UpdateActivityController {
    
    @FXML private VBox updateActivityContainer;
    @FXML private ChoiceBox<String> idProgettoCombo;
    @FXML private ChoiceBox<String> idLottoCombo;
    @FXML private ChoiceBox<String> idColtivatoreCombo;
    @FXML private ChoiceBox<String> tipoCombo;
    @FXML private ChoiceBox<String> statoCombo;
    @FXML private DatePicker activityStartDatePicker;
    @FXML private DatePicker activityEndDatePicker;

    AddAndUpdateService updateService;

    public void initialize() {
        updateService = AddAndUpdateService.getInstance();

        preparaCampi();
        riempiCampi();
        eliminaScelteImpossibili();
    }

    private void preparaCampi() {
        tipoCombo.getItems().addAll("Semina", "Irrigazione", "Raccolta");
        statoCombo.getItems().addAll("Pianificata", "In corso", "Completata", "Fallita");
    }
    private void riempiCampi() {
        Attivita attivita = (Attivita) Router.getContentContainer().getUserData(); // Per semplice passaggio di dati tra schermate
    
        idProgettoCombo.setValue(attivita.getNomeProgetto());
        idLottoCombo.setValue(attivita.getIdLotto());
        idColtivatoreCombo.setValue(attivita.getInfoColtivatore());
        tipoCombo.setValue(attivita.getTipo());
        statoCombo.setValue(attivita.getStato());
        activityStartDatePicker.setValue(attivita.getDataInizio());
        activityEndDatePicker.setValue(attivita.getDataFine());
    }
    private void eliminaScelteImpossibili() {
        String stato = statoCombo.getValue();

        if (stato == "Completata" || stato == "Fallita") {
            statoCombo.getItems().removeIf(item -> !item.equals("Completata") && !item.equals("Fallita"));
        } else if (stato == "In corso") {
            statoCombo.getItems().removeIf(item -> item.equals("Pianificata"));
        }
    }

    @FXML private void handleUpdateActivity() {
        try {
            String idProgetto = idProgettoCombo.getValue();
            String idLotto = idLottoCombo.getValue();
            String idColtivatore = idColtivatoreCombo.getValue();
            String stato = statoCombo.getValue();
            String activityStartDate = activityStartDatePicker.getValue().toString();
            String activityEndDate = activityEndDatePicker.getValue().toString();

            updateService.updateActivity(idProgetto, idLotto, idColtivatore, idColtivatore, stato, activityStartDate, activityEndDate);

            Router.getInstance().showSnackbar("Attività modificata con successo.");
            Router.getInstance().loadContent("home/allActivitiesBlock");
        } catch (Exception e) {
            FocusUtil.setFocusTo(updateActivityContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void handleGoBackToAllActivities() {
        Router.getInstance().loadContent("home/allActivitiesBlock");
    }
}
