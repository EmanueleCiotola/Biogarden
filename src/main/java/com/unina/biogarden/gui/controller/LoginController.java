package com.unina.biogarden.gui.controller;
import com.unina.biogarden.router.Router;
import com.unina.biogarden.service.LoginService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Node;



public class LoginController {
    @FXML private StackPane loginPage;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private LoginService loginService;

    public void initialize() {
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

        if (loginService.login(username, password) != null) {
            Router.getInstance().navigateTo("homePage");
        }
    }

    @FXML
    private void goToSignup() {
        Router.getInstance().navigateTo("signupPage");
    }
}
