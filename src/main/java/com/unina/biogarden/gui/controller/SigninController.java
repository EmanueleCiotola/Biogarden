package com.unina.biogarden.gui.controller;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.router.Router;
import com.unina.biogarden.service.SigninService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class SigninController {
    @FXML private StackPane signinPage;
    @FXML private VBox primoBlocco;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private Button confermaPrimoBloccoSigninButton;
    @FXML private VBox secondoBlocco;
    @FXML private TextField codiceFiscaleField;
    @FXML private TextField usernameField;
    @FXML private Button indietroSecondoBloccoSignin;
    @FXML private Button confermaSecondoBloccoSigninButton;
    @FXML private VBox terzoBlocco;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField ripetiPasswordField;
    @FXML private Button indietroTerzoBloccoSignin;
    @FXML private Button signinButton;

    private SigninService signinService;

    public void initialize() {
        Platform.runLater(() -> signinPage.requestFocus());
        signinPage.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) signinPage.requestFocus();
        });

        signinService = new SigninService();
    }

    @FXML
    private void handleConfermaPrimoBloccoSigninButton() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        //TODO: implementare logica per il primo blocco

        if (signinService.isPrimoBloccoValido(nome, cognome)) {
            Router.getInstance().switchBlocks(primoBlocco, secondoBlocco);
        }
    }
    @FXML
    private void handleIndietroSecondoBloccoSignin() {
        Router.getInstance().switchBlocks(secondoBlocco, primoBlocco);
    }
    
    @FXML
    private void handleConfermaSecondoBloccoSigninButton() {
        String codiceFiscale = codiceFiscaleField.getText().trim().toUpperCase();
        String username = usernameField.getText().trim();
        
        if (signinService.isSecondoBloccoValido(codiceFiscale, username)) {
            Router.getInstance().switchBlocks(secondoBlocco, terzoBlocco);
        }
    }
    @FXML
    private void handleIndietroTerzoBloccoSignin() {
        Router.getInstance().switchBlocks(terzoBlocco, secondoBlocco);
    }

    @FXML
    private void handleSignin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // TODO
        signinService.signin(username, password);
    }

    @FXML
    private void goToLogin() {
        try {
            Router.getInstance().navigateTo("loginPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
