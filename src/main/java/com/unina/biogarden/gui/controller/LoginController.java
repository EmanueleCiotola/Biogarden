package com.unina.biogarden.gui.controller;

import com.unina.biogarden.router.Router;
import com.unina.biogarden.service.LoginService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Node;


public class LoginController {
    @FXML private VBox loginPage;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private LoginService loginService;

    public void initialize() {
        loginPage.setFocusTraversable(true);
        Platform.runLater(() -> loginPage.requestFocus());
        loginPage.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) loginPage.requestFocus();
        });

        loginService = new LoginService();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        loginService.login(username, password);
    }

    @FXML
    private void goToSignin() {
        try {
            Router.getInstance().navigateTo("signinPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
