package com.unina.biogarden.gui.controller;

import com.unina.biogarden.service.AuthService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.model.UtenteProprietario;
import com.unina.biogarden.router.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
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
    @FXML private VBox bloccoPartitaIvaSignup;
    @FXML private ToggleGroup sceltaTipoUtenteSignupToggle;
    @FXML private RadioButton coltivatoreRadio;
    @FXML private RadioButton proprietarioRadio;
    @FXML private TextField partitaIvaSignupField;
    @FXML private Button indietroDaBloccoPartitaIvaSignupButton;
    @FXML private Button confermaBloccoPartitaIvaSignupButton;
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
    private String partitaIvaTemporanea;
    private Utente utenteInCostruzione;

    public void initialize() {
        FocusUtil.setFocusTo(authPage);
        authService = AuthService.getInstance();

        sceltaTipoUtenteSignupToggle.selectedToggleProperty().addListener((_, _, selectedToggle) -> {
            onTipoUtenteChanged(selectedToggle);
        });
    }
    private void onTipoUtenteChanged(Toggle selectedToggle) {
        if (selectedToggle == proprietarioRadio) {
            partitaIvaSignupField.setDisable(false);
            partitaIvaSignupField.setText(partitaIvaTemporanea);
        } else {
            partitaIvaTemporanea = partitaIvaSignupField.getText();
            partitaIvaSignupField.clear();
            partitaIvaSignupField.setDisable(true);
        }
    }

    @FXML private void handleLogin() {
        String username = usernameLoginField.getText().trim();
        String password = passwordLoginField.getText().trim();
        
        try {            
            authService.login(username, password);
            Router.getInstance().navigateTo("homePage");
        } catch (Exception e) {
            FocusUtil.setFocusTo(authPage);
            SnackbarController.show(e.getMessage());
        }
    }
    
    @FXML private void handleConfermaBloccoPartitaIvaSignup() {
        Toggle selectedToggle = sceltaTipoUtenteSignupToggle.getSelectedToggle();

        try {
            utenteInCostruzione = creaUtenteDaToggle(selectedToggle);

            Router.getInstance().switchBlocks(bloccoPartitaIvaSignup, bloccoNomeCognomeSignup);
            FocusUtil.setFocusTo(authPage);
        } catch (Exception e) {
            FocusUtil.setFocusTo(authPage);
            SnackbarController.show(e.getMessage());
        }
    }
    private Utente creaUtenteDaToggle(Toggle selectedToggle) throws Exception {
        if (selectedToggle == coltivatoreRadio) {
            return new UtenteColtivatore();
        } else {
            String partitaIva = partitaIvaSignupField.getText().trim();

            authService.validaBloccoPartitaIvaSignup(partitaIva);
            UtenteProprietario proprietario = new UtenteProprietario();
            proprietario.setPartitaIva(partitaIva);

            return proprietario;
        }
    }

    @FXML private void handleConfermaBloccoNomeCognomeSignup() {
        String nome = nomeSignupField.getText().trim();
        String cognome = cognomeSignupField.getText().trim();

        try {
            authService.validaBloccoNomeCognomeSignup(nome, cognome);

            utenteInCostruzione.setNome(nome);
            utenteInCostruzione.setCognome(cognome);

            Router.getInstance().switchBlocks(bloccoNomeCognomeSignup, bloccoCodFiscUsernameSignup);
            FocusUtil.setFocusTo(authPage);
        } catch (Exception e) {
            FocusUtil.setFocusTo(authPage);
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML private void handleIndietroBloccoPartitaIvaSignup() {
        Router.getInstance().switchBlocks(bloccoNomeCognomeSignup, bloccoPartitaIvaSignup);
        FocusUtil.setFocusTo(authPage);
    }
    
    @FXML private void handleConfermaBloccoCodFiscUsernameSignup() { 
        String codiceFiscale = codiceFiscaleSignupField.getText().trim().toUpperCase();
        String username = usernameSignupField.getText().trim();
        
        try {
            authService.validaBloccoCodFiscUsernameSignup(codiceFiscale, username);

            utenteInCostruzione.setCodiceFiscale(codiceFiscale);
            utenteInCostruzione.setUsername(username);

            Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoPasswordSignup);
            FocusUtil.setFocusTo(authPage);
        } catch (Exception e) {
            FocusUtil.setFocusTo(authPage);
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML private void handleIndietroBloccoCodFiscUsernameSignup() {
        Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoNomeCognomeSignup);
        FocusUtil.setFocusTo(authPage);
    }

    @FXML private void handleConfermaBloccoPasswordSignup() {
        String password = passwordSignupField.getText().trim();
        String ripetizionePassword = ripetiPasswordSignupField.getText().trim();

        try {
            authService.validaBloccoPasswordSignup(password, ripetizionePassword);
            utenteInCostruzione.setPassword(password);
            authService.signup(utenteInCostruzione);

            Router.getInstance().navigateTo("homePage");
        } catch (Exception e) {
            FocusUtil.setFocusTo(authPage);
            SnackbarController.show(e.getMessage());
        }
    }
    @FXML private void handleIndietroDaBloccoPasswordSignup() {
        Router.getInstance().switchBlocks(bloccoPasswordSignup, bloccoCodFiscUsernameSignup);
        FocusUtil.setFocusTo(authPage);
    }


    @FXML public void goToSignup() {
        Router.getInstance().switchBlocks(loginContainer, signupContainer);
        FocusUtil.setFocusTo(authPage);
    }
    @FXML public void goToLogin() {
        Router.getInstance().switchBlocks(signupContainer, loginContainer);
        FocusUtil.setFocusTo(authPage);
    }
}
