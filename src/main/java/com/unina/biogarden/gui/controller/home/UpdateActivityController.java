package com.unina.biogarden.gui.controller.home;

import java.time.LocalDate;

import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class UpdateActivityController {
    
    @FXML private VBox updateActivityContainer;
    @FXML private ChoiceBox<Progetto> idProgettoCombo;
    @FXML private ChoiceBox<Lotto> idLottoCombo;
    @FXML private ChoiceBox<UtenteColtivatore> idColtivatoreCombo;
    @FXML private ChoiceBox<String> tipoCombo;
    @FXML private ChoiceBox<String> statoCombo;
    @FXML private DatePicker activityStartDatePicker;
    @FXML private DatePicker activityEndDatePicker;

    AddAndUpdateService updateService;
    Attivita attivita;

    public void initialize() {
        updateService = AddAndUpdateService.getInstance();

        FocusUtil.setFocusTo(updateActivityContainer);

        preparaCampi();
        riempiCampi();
        eliminaScelteImpossibili();
    }

    private void preparaCampi() {
        tipoCombo.getItems().addAll("Semina", "Irrigazione", "Raccolta");
        statoCombo.getItems().addAll("Pianificata", "In corso", "Completata", "Fallita");
    }
    private void riempiCampi() {
        try {
            attivita = (Attivita) Router.getContentContainer().getUserData(); // Per semplice passaggio di dati tra schermate
            
            idProgettoCombo.setValue(updateService.getProgettoByAttivita(attivita));
            idLottoCombo.setValue(updateService.getLottoByAttivita(attivita));
            idColtivatoreCombo.setValue(updateService.getColtivatoreByAttivita(attivita));
    
            tipoCombo.setValue(attivita.getTipo());
            statoCombo.setValue(attivita.getStato());
            activityStartDatePicker.setValue(attivita.getDataInizio());
            activityEndDatePicker.setValue(attivita.getDataFine());
        } catch (Exception e) {
            FocusUtil.setFocusTo(updateActivityContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
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
            int idAttivita = attivita.getIdAttivita();
            int idProgetto = Integer.parseInt(idProgettoCombo.getValue().getIdProgetto());
            String nomeProgetto = idProgettoCombo.getValue().getNomeProgetto();
            String idLotto = idLottoCombo.getValue().getIdLotto();
            String idColtivatore = idColtivatoreCombo.getValue().getCodiceFiscale();
            String infoColtivatore = attivita.getInfoColtivatore();
            LocalDate activityStartDate = activityStartDatePicker.getValue();
            LocalDate activityEndDate = activityEndDatePicker.getValue();
            String tipo = attivita.getTipo();
            String stato = statoCombo.getValue();

            Attivita attivita = new Attivita(idAttivita, idProgetto, nomeProgetto, idColtivatore, infoColtivatore, idLotto, activityStartDate, activityEndDate, tipo, stato);
            
            updateService.updateActivity(attivita);

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
