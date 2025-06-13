package com.unina.biogarden.gui.controller;

import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.router.Router;
import com.unina.biogarden.util.Sessione;
import com.unina.biogarden.service.LoginService;
import com.unina.biogarden.dto.Utente;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;



public class LoginController {
    @FXML private StackPane loginPage;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private LoginService loginService;

    public void initialize() {
        FocusUtil.removeInitialFocus(loginPage);
        loginService = new LoginService();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Utente utente = loginService.login(username, password);
        if (utente != null) {
            Sessione.setUtenteCorrente(utente);
            Router.getInstance().navigateTo("homePage");
        }
    }

    @FXML
    private void goToSignup() {
        Router.getInstance().navigateTo("signupPage");
    }
}
