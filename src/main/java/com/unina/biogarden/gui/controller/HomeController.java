package com.unina.biogarden.gui.controller;

import com.unina.biogarden.router.Router;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomeController {
    @FXML
    private Button BackToLoginButton;

    @FXML private StackPane homePageStackPane;
    @FXML private Label emailLabel;

     public void initialize() {
        homePageStackPane.setFocusTraversable(true);
        Platform.runLater(() -> homePageStackPane.requestFocus());
        homePageStackPane.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) homePageStackPane.requestFocus();
        });
    }

    public void postInitialize() {
        emailLabel.setText("Benvenuto");
    }

    @FXML
    public void goBackToHome() {
        Router.getInstance().navigateTo("authPage");
    }
}
