package com.unina.biogarden.gui.controller;

import com.unina.biogarden.service.AuthService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.router.Router;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.dto.UtenteProprietario;

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
    @FXML private VBox bloccoNomeCognomeSignup;
    @FXML private TextField nomeSignupField;
    @FXML private TextField cognomeSignupField;
    @FXML private Button confermaBloccoNomeCognomeSignupButton;
    @FXML private VBox bloccoCodFiscUsernameSignup;
    @FXML private TextField codiceFiscaleSignupField;
    @FXML private TextField usernameSignupField;
    @FXML private Button indietroDaBloccoCodFiscUsernameSignupButton;
    @FXML private Button confermaBloccoCodFiscUsernameSignupButton;
    @FXML private VBox bloccoPasswordSignup;
    @FXML private PasswordField passwordSignupField;
    @FXML private PasswordField ripetiPasswordSignupField;
    @FXML private Button indietroDaBloccoPasswordSignupButton;
    @FXML private Button confermaBloccoPasswordSignupButton;

    private AuthService authService;
    private Utente utenteInCostruzione;

    public void initialize() {
        FocusUtil.setFocusTo(authPage);
        authService = AuthService.getInstance();  
    }

    @FXML
    private void handleLogin() {
        String username = usernameLoginField.getText().trim();
        String password = passwordLoginField.getText().trim();
        
        try {            
            authService.login(username, password);
            Router.getInstance().navigateTo("homePage");
        } catch (Exception e) {
            SnackbarController.show(e.getMessage());
        }
    }
    
    @FXML
    private void handleConfermaBloccoNomeCognomeSignup() {
        String nome = nomeSignupField.getText().trim();
        String cognome = cognomeSignupField.getText().trim();

        try {
            authService.validaPrimoBloccoSignup(nome, cognome);

            utenteInCostruzione = new UtenteProprietario(); // TODO cambiare in modo appropriato
            utenteInCostruzione.setNome(nome);
            utenteInCostruzione.setCognome(cognome);

            Router.getInstance().switchBlocks(bloccoNomeCognomeSignup, bloccoCodFiscUsernameSignup);
            FocusUtil.setFocusTo(authPage);
        } catch (Exception e) {
            SnackbarController.show(e.getMessage());
        }
    }
    
    @FXML
    private void handleConfermaBloccoCodFiscUsernameSignup() {
        String codiceFiscale = codiceFiscaleSignupField.getText().trim().toUpperCase();
        String username = usernameSignupField.getText().trim();
        
        try {
            authService.validaSecondoBloccoSignup(codiceFiscale, username);

            utenteInCostruzione.setCodiceFiscale(codiceFiscale);
            utenteInCostruzione.setUsername(username);

            Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoPasswordSignup);
            FocusUtil.setFocusTo(authPage);
        } catch (Exception e) {
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML
    private void handleIndietroBloccoCodFiscUsernameSignup() {
        Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoNomeCognomeSignup);
        FocusUtil.setFocusTo(authPage);
    }
    

    @FXML
    private void handleConfermaBloccoPasswordSignup() {
        String password = passwordSignupField.getText().trim();
        String ripetizionePassword = ripetiPasswordSignupField.getText().trim();

        try {
            authService.validaTerzoBloccoSignup(password, ripetizionePassword);
            utenteInCostruzione.setPassword(password);
            authService.signup(utenteInCostruzione);
            Router.getInstance().navigateTo("homePage");
        } catch (Exception e) {
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML
    private void handleIndietroDaBloccoPasswordSignup() {
        Router.getInstance().switchBlocks(bloccoPasswordSignup, bloccoCodFiscUsernameSignup);
        FocusUtil.setFocusTo(authPage);
    }


    @FXML
    public void goToSignup() {
        Router.getInstance().switchBlocks(loginContainer, signupContainer);
        FocusUtil.setFocusTo(authPage);
    }
    public void goToLogin() {
        Router.getInstance().switchBlocks(signupContainer, loginContainer);
        FocusUtil.setFocusTo(authPage);
    }
}
