package com.unina.biogarden.gui.controller;

import com.unina.biogarden.router.Router;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomeController {
    @FXML private StackPane homePageStackPane;
    @FXML private Label emailLabel;
    private String email;

     public void initialize() {
        homePageStackPane.setFocusTraversable(true);
        Platform.runLater(() -> homePageStackPane.requestFocus());
        homePageStackPane.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) homePageStackPane.requestFocus();
        });
    }

    public void postInitialize() {
        // email = mainController.getEmailUtente();
        emailLabel.setText("Benvenuto, " + email);
    }

    @FXML
    public void goBackToHome() {
        Router.getInstance().navigateTo("loginPage");
    }
}
