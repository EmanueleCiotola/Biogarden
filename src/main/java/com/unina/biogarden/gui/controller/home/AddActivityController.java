package com.unina.biogarden.gui.controller.home;

import java.time.LocalDate;

import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.layout.VBox;

public class AddActivityController {
    
    @FXML private VBox addActivityContainer;
    @FXML private ChoiceBox<String> idProgettoCombo;
    @FXML private ChoiceBox<String> idLottoCombo;
    @FXML private ChoiceBox<String> idColtivatoreCombo;
    @FXML private ChoiceBox<String> tipoCombo;
    @FXML private ChoiceBox<String> statoCombo;
    @FXML private ChoiceBox<String> tipoSeminaCombo;
    @FXML private ChoiceBox<String> colturaCombo;
    @FXML private DatePicker activityStartDatePicker;
    @FXML private Spinner<Double> raccoltaQuantitaPrevistaSpinner;

    AddAndUpdateService addNewService;

    public void initialize() {
        addNewService = AddAndUpdateService.getInstance();
        
        getComboData();
        setComboDefault();

        setupSpinner();

        listenerCheck();
        setupListeners();
    }
    
    private void getComboData() {
        try {
            idProgettoCombo.getItems().addAll(addNewService.getNomiProgettiAttiviProprietario());
            idLottoCombo.getItems().addAll(addNewService.getNomiLottiProprietario());
            idColtivatoreCombo.getItems().addAll(addNewService.getInfoColtivatoriDisponibili());
            tipoCombo.getItems().addAll("Semina", "Irrigazione", "Raccolta");
            statoCombo.getItems().addAll("Pianificata", "In corso", "Completata", "Fallita");
            tipoSeminaCombo.getItems().addAll("Erbe aromatiche", "Insalata", "Pomodori");
        } catch (Exception e) {
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private void setComboDefault() {
        idProgettoCombo.getSelectionModel().select(0);
        tipoCombo.getSelectionModel().select(0);
        idLottoCombo.getSelectionModel().select(0);
        idColtivatoreCombo.getSelectionModel().select(0);
        tipoCombo.getSelectionModel().select(0);
        statoCombo.getSelectionModel().select(0);
        statoCombo.getSelectionModel().select(0);
    }
    private void setupColturaCombo(String selectedProgetto, String selectedLotto) {
        try {
            colturaCombo.getItems().addAll(addNewService.getNomiColtureLotto(selectedProgetto, selectedLotto));
            colturaCombo.getSelectionModel().select(0);
            colturaCombo.setDisable(false);
        } catch (Exception e) {
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private void setupListeners() {
        tipoCombo.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> listenerCheck());
        idProgettoCombo.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> listenerCheck());
        idLottoCombo.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> listenerCheck());
    }
    private void listenerCheck() {
        String selectedProgetto = idProgettoCombo.getValue();
        String selectedLotto = idLottoCombo.getValue();
        String selectedTipo = tipoCombo.getValue();

        checkTipoSeminaCombo(selectedProgetto, selectedLotto, selectedTipo);
        checkQuantitaSpinner(selectedTipo);
    }
    private void checkTipoSeminaCombo(String selectedProgetto, String selectedLotto, String selectedTipo) {
        if ("Semina".equals(selectedTipo)) {
            tipoSeminaCombo.setDisable(false);
            tipoSeminaCombo.getSelectionModel().select(0);
        } else {
            tipoSeminaCombo.getSelectionModel().clearSelection();
            tipoSeminaCombo.setDisable(true);
            checkColturaCombo(selectedProgetto, selectedLotto);
        }
    }
    private void checkColturaCombo(String selectedProgetto, String selectedLotto) {
        colturaCombo.getItems().clear();
        
        if (selectedProgetto != null && selectedLotto != null) {
            setupColturaCombo(selectedProgetto, selectedLotto);
        } else {
            colturaCombo.setDisable(true);
        }
    }
    private void checkQuantitaSpinner(String selectedTipo) {
        if ("Raccolta".equals(selectedTipo)) {
            raccoltaQuantitaPrevistaSpinner.setDisable(false);
        } else {
            raccoltaQuantitaPrevistaSpinner.setDisable(true);
            raccoltaQuantitaPrevistaSpinner.getValueFactory().setValue(0.0d);
        }
    }

    private void setupSpinner() {
        DoubleSpinnerValueFactory valueFactory = new DoubleSpinnerValueFactory(0.1f, 1000000000.0f, 0.0f, 1.0f);
        raccoltaQuantitaPrevistaSpinner.setValueFactory(valueFactory);
    }

    @FXML private void handleAddNewActivity() {
        try {
            String idProgetto = idProgettoCombo.getValue();
            String idLotto = idLottoCombo.getValue();
            String idColtivatore = idColtivatoreCombo.getValue();
            String tipo = tipoCombo.getValue();
            String stato = statoCombo.getValue();
            String tipoSemina = tipoSeminaCombo.getValue();
            String idColtura = colturaCombo.getValue();
            LocalDate activityStartDate = activityStartDatePicker.getValue();
            String raccoltaQuantitaPrevista = raccoltaQuantitaPrevistaSpinner.getValue().toString();

            addNewService.addNewActivity(idProgetto, idLotto, idColtivatore, tipo, stato, activityStartDate, tipoSemina, idColtura, raccoltaQuantitaPrevista);

            Router.getInstance().showSnackbar("Attività aggiunta con successo.");
            Router.getInstance().loadContent("home/addNewBlock");
        } catch (Exception e) {
            FocusUtil.setFocusTo(addActivityContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void handleGoBackToAddNewBlock() {
        Router.getInstance().loadContent("home/addNewBlock");
    }

}
