package com.unina.biogarden.gui.controller;

import com.unina.biogarden.router.Router;
import com.unina.biogarden.service.SigninService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Node;


public class SigninController {
    @FXML private VBox signinPage;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signinButton;

    private SigninService signinService;

    public void initialize() {
        signinPage.setFocusTraversable(true);
        Platform.runLater(() -> signinPage.requestFocus());
        signinPage.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) signinPage.requestFocus();
        });

        signinService = new SigninService();
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
