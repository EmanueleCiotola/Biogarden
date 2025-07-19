package com.unina.biogarden.gui.controller.home.widget;

import com.unina.biogarden.model.Progetto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjectCardController {
    @FXML private Label nomeProgettoLabel;
    @FXML private Label dataInizioLabel;
    @FXML private Label dataFineLabel;
    @FXML private Label statoLabel;

    public void setData(Progetto progetto) {
        nomeProgettoLabel.setText(progetto.getNomeProgetto());
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
        statoLabel.setText(progetto.getStato());
    }
}
