package com.unina.biogarden.gui.controller.home.widget;

import com.unina.biogarden.model.Attivita;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ActivityCardController {
    @FXML private Label nomeProgettoLabel;
    @FXML private Label infoColtivatoreLabel;
    @FXML private Label idLottoLabel;
    @FXML private Label dataInizioLabel;
    @FXML private Label dataFineLabel;
    @FXML private Label tipoLabel;
    @FXML private Label statoLabel;

    public void setData(Attivita attivita) {
        nomeProgettoLabel.setText(attivita.getNomeProgetto());
        infoColtivatoreLabel.setText(attivita.getInfoColtivatore());
        idLottoLabel.setText(attivita.getIdLotto());
        dataInizioLabel.setText(attivita.getDataInizio().toString());
        dataFineLabel.setText(attivita.getDataFine().toString());
        tipoLabel.setText(attivita.getTipo());
        statoLabel.setText(attivita.getStato());
    }
}