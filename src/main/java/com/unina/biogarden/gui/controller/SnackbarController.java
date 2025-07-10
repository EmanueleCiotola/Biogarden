package com.unina.biogarden.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SnackbarController {
    @FXML private Label snackBarLabel;

    public void setMessage(String message) {
        snackBarLabel.setText(message);
    }
}
