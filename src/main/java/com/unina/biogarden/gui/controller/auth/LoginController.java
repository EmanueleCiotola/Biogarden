package com.unina.biogarden.gui.controller.auth;

import com.unina.biogarden.service.LoginService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML private VBox loginContainer;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;

    private LoginService loginService;

    public void initialize() {
        FocusUtil.setFocusTo(loginContainer);
        loginService = LoginService.getInstance();
    }

    @FXML private void handleLogin() {
        String username = usernameLoginField.getText().trim();
        String password = passwordLoginField.getText().trim();
        
        try {            
            loginService.login(username, password);
            Router.getInstance().navigateTo("home/homePage");
        } catch (Exception e) {
            FocusUtil.setFocusTo(loginContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    @FXML private void goToSignup() {
        Router.getInstance().loadContent("auth/signupBlock");
    }
    
}
