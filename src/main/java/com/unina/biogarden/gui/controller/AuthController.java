package com.unina.biogarden.gui.controller;

import com.unina.biogarden.service.AuthService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.router.Router;
import com.unina.biogarden.dto.Utente;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class AuthController {
    @FXML private StackPane authPage;
    @FXML private VBox loginContainer;
    @FXML private VBox signupContainer;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;
    @FXML private Button loginButton;
    @FXML private VBox primoBloccoSignup;
    @FXML private TextField nomeSignupField;
    @FXML private TextField cognomeSignupField;
    @FXML private Button confermaPrimoBloccoSignupButton;
    @FXML private VBox secondoBloccoSignup;
    @FXML private TextField codiceFiscaleSignupField;
    @FXML private TextField usernameSignupField;
    @FXML private Button confermaSecondoBloccoSignup;
    @FXML private VBox terzoBloccoSignup;
    @FXML private PasswordField passwordSignupField;
    @FXML private PasswordField ripetiPasswordSignupField;
    @FXML private Button indietroTerzoBloccoSignupButton;
    @FXML private Button signupButton;

    private AuthService authService;

    public void initialize() {
        FocusUtil.setFocusTo(authPage);
        authService = AuthService.getInstance();  
    }

    @FXML
    private void handleLogin() {
        String username = usernameLoginField.getText();
        String password = passwordLoginField.getText();
        
        try {            
            Utente utente = authService.login(username, password);
            if (utente != null) Router.getInstance().navigateTo("homePage");
        } catch (Exception e) {
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML
    public void goToSignup() {
        Router.getInstance().switchBlocks(loginContainer, signupContainer);
        FocusUtil.setFocusTo(authPage);
    }

    @FXML
    private void handleConfermaPrimoBloccoSignup() {
        String nome = nomeSignupField.getText().trim();
        String cognome = cognomeSignupField.getText().trim();

        if (authService.isPrimoBloccoSignupValido(nome, cognome)) {
            Router.getInstance().switchBlocks(primoBloccoSignup, secondoBloccoSignup);
            FocusUtil.setFocusTo(authPage);
        }
    }
    @FXML
    private void handleIndietroSecondoBloccoSignup() {
        Router.getInstance().switchBlocks(secondoBloccoSignup, primoBloccoSignup);
        FocusUtil.setFocusTo(authPage);
    }
    
    @FXML
    private void handleConfermaSecondoBloccoSignup() {
        String codiceFiscale = codiceFiscaleSignupField.getText().trim().toUpperCase();
        String username = usernameSignupField.getText().trim();
        
        if (authService.isSecondoBloccoSignupValido(codiceFiscale, username)) {
            Router.getInstance().switchBlocks(secondoBloccoSignup, terzoBloccoSignup);
            FocusUtil.setFocusTo(authPage);
        }
    }
    @FXML
    private void handleIndietroTerzoBloccoSignup() {
        Router.getInstance().switchBlocks(terzoBloccoSignup, secondoBloccoSignup);
        FocusUtil.setFocusTo(authPage);
    }

    @FXML
    private void handleSignup() {
        String password = passwordSignupField.getText();
        String ripetiPassword = ripetiPasswordSignupField.getText();

        if (authService.isTerzoBloccoSignupValido(password, ripetiPassword)) {
            Utente utente = costruisciUtente();
            Utente registrato = authService.signup(utente);
            if (registrato != null) Router.getInstance().navigateTo("homePage");
        }
    }

    private Utente costruisciUtente() {
        return authService.creaUtente(
            nomeSignupField.getText().trim(),
            cognomeSignupField.getText().trim(),
            codiceFiscaleSignupField.getText().trim().toUpperCase(),
            usernameSignupField.getText().trim()
        );
    }

    public void goToLogin() {
        Router.getInstance().switchBlocks(signupContainer, loginContainer);
        FocusUtil.setFocusTo(authPage);
    }
}
