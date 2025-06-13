package com.unina.biogarden.gui.controller;

import com.unina.biogarden.router.Router;
import com.unina.biogarden.service.SignupService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class SignupController {
    @FXML private StackPane signupPage;
    @FXML private VBox primoBlocco;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private Button confermaPrimoBloccoSignupButton;
    @FXML private VBox secondoBlocco;
    @FXML private TextField codiceFiscaleField;
    @FXML private TextField usernameField;
    @FXML private Button indietroSecondoBloccoSignupButton;
    @FXML private Button confermaSecondoBloccoSignupButton;
    @FXML private VBox terzoBlocco;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField ripetiPasswordField;
    @FXML private Button indietroTerzoBloccoSignupButton;
    @FXML private Button signupButton;

    private SignupService signupService;

    public void initialize() {
        Platform.runLater(() -> signupPage.requestFocus());
        signupPage.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) signupPage.requestFocus();
        });

        signupService = new SignupService();
    }

    @FXML
    private void handleConfermaPrimoBloccoSignup() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();

        if (signupService.isPrimoBloccoValido(nome, cognome)) {
            Router.getInstance().switchBlocks(primoBlocco, secondoBlocco);
        }
    }
    @FXML
    private void handleIndietroSecondoBloccoSignup() {
        Router.getInstance().switchBlocks(secondoBlocco, primoBlocco);
    }
    
    @FXML
    private void handleConfermaSecondoBloccoSignup() {
        String codiceFiscale = codiceFiscaleField.getText().trim().toUpperCase();
        String username = usernameField.getText().trim();
        
        if (signupService.isSecondoBloccoValido(codiceFiscale, username)) {
            Router.getInstance().switchBlocks(secondoBlocco, terzoBlocco);
        }
    }
    @FXML
    private void handleIndietroTerzoBloccoSignup() {
        Router.getInstance().switchBlocks(terzoBlocco, secondoBlocco);
    }

    @FXML
    private void handleSignup() {
        String password = passwordField.getText();
        String ripetiPassword = ripetiPasswordField.getText();

        if (signupService.isTerzoBloccoValido(password, ripetiPassword)) {
            if (signupService.signup(password) != null) {
                Router.getInstance().navigateTo("homePage");
            }
        }
    }

    @FXML
    private void goToLogin() {
        Router.getInstance().navigateTo("loginPage");
    }
}
