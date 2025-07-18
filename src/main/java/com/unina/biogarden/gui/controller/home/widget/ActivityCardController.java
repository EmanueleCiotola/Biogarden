package com.unina.biogarden.gui.controller.home.widget;

import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ActivityCardController {
    @FXML private Label nomeProgettoLabel;
    @FXML private Label infoColtivatoreLabel;
    @FXML private Label idLottoLabel;
    @FXML private Label dataInizioLabel;
    @FXML private Label dataFineLabel;
    @FXML private Label tipoLabel;
    @FXML private Label statoLabel;
    @FXML private Button modificaAttivitaButton;

    private Attivita attivitaCorrente;

    public void setData(Attivita attivita) {
        nomeProgettoLabel.setText(attivita.getNomeProgetto());
        infoColtivatoreLabel.setText(attivita.getInfoColtivatore());
        idLottoLabel.setText(attivita.getIdLotto());
        dataInizioLabel.setText(attivita.getDataInizio().toString());
        dataFineLabel.setText(attivita.getDataFine() != null ? attivita.getDataFine().toString() : "Non definita");
        tipoLabel.setText(attivita.getTipo());
        statoLabel.setText(attivita.getStato());

        this.attivitaCorrente = attivita;
    }

    @FXML private void handleModificaAttivita() {
        Router.getContentContainer().setUserData(attivitaCorrente); // Per semplice passaggio di dati tra schermate
        Router.getInstance().loadContent("home/updateActivityBlock");
    }

}