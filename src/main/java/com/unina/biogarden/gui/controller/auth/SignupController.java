package com.unina.biogarden.gui.controller.auth;

import com.unina.biogarden.service.SignupService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.model.UtenteProprietario;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class SignupController {
    @FXML private VBox signupContainer;
    @FXML private VBox bloccoPartitaIvaSignup;
    @FXML private ToggleGroup sceltaTipoUtenteSignupToggle;
    @FXML private RadioButton coltivatoreRadio;
    @FXML private RadioButton proprietarioRadio;
    @FXML private TextField partitaIvaSignupField;
    @FXML private VBox bloccoNomeCognomeSignup;
    @FXML private TextField nomeSignupField;
    @FXML private TextField cognomeSignupField;
    @FXML private VBox bloccoCodFiscUsernameSignup;
    @FXML private TextField codiceFiscaleSignupField;
    @FXML private TextField usernameSignupField;
    @FXML private VBox bloccoPasswordSignup;
    @FXML private PasswordField passwordSignupField;
    @FXML private PasswordField ripetiPasswordSignupField;

    private SignupService signupService;
    private String partitaIvaTemporanea;
    private Utente utenteInCostruzione;

    public void initialize() {
        FocusUtil.setFocusTo(signupContainer);
        signupService = SignupService.getInstance();

        sceltaTipoUtenteSignupToggle.selectedToggleProperty().addListener((_, _, selectedToggle) -> onTipoUtenteChanged(selectedToggle));
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

    @FXML private void handleConfermaBloccoPartitaIvaSignup() {
        Toggle selectedToggle = sceltaTipoUtenteSignupToggle.getSelectedToggle();

        try {
            utenteInCostruzione = creaUtenteDaToggle(selectedToggle);

            Router.getInstance().switchBlocks(bloccoPartitaIvaSignup, bloccoNomeCognomeSignup);
            FocusUtil.setFocusTo(signupContainer);
        } catch (Exception e) {
            FocusUtil.setFocusTo(signupContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private Utente creaUtenteDaToggle(Toggle selectedToggle) throws Exception {
        if (selectedToggle == coltivatoreRadio) {
            return new UtenteColtivatore();
        } else {
            String partitaIva = partitaIvaSignupField.getText().trim();

            signupService.validaBloccoPartitaIvaSignup(partitaIva);
            UtenteProprietario proprietario = new UtenteProprietario();
            proprietario.setPartitaIva(partitaIva);

            return proprietario;
        }
    }

    @FXML private void handleConfermaBloccoNomeCognomeSignup() {
        String nome = nomeSignupField.getText().trim();
        String cognome = cognomeSignupField.getText().trim();

        try {
            signupService.validaBloccoNomeCognomeSignup(nome, cognome);

            utenteInCostruzione.setNome(nome);
            utenteInCostruzione.setCognome(cognome);

            Router.getInstance().switchBlocks(bloccoNomeCognomeSignup, bloccoCodFiscUsernameSignup);
            FocusUtil.setFocusTo(signupContainer);
        } catch (Exception e) {
            FocusUtil.setFocusTo(signupContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void handleIndietroBloccoPartitaIvaSignup() {
        Router.getInstance().switchBlocks(bloccoNomeCognomeSignup, bloccoPartitaIvaSignup);
        FocusUtil.setFocusTo(signupContainer);
    }
    
    @FXML private void handleConfermaBloccoCodFiscUsernameSignup() { 
        String codiceFiscale = codiceFiscaleSignupField.getText().trim().toUpperCase();
        String username = usernameSignupField.getText().trim();
        
        try {
            signupService.validaBloccoCodFiscUsernameSignup(codiceFiscale, username);

            utenteInCostruzione.setCodiceFiscale(codiceFiscale);
            utenteInCostruzione.setUsername(username);

            Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoPasswordSignup);
            FocusUtil.setFocusTo(signupContainer);
        } catch (Exception e) {
            FocusUtil.setFocusTo(signupContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void handleIndietroBloccoCodFiscUsernameSignup() {
        Router.getInstance().switchBlocks(bloccoCodFiscUsernameSignup, bloccoNomeCognomeSignup);
        FocusUtil.setFocusTo(signupContainer);
    }

    @FXML private void handleConfermaBloccoPasswordSignup() {
        String password = passwordSignupField.getText().trim();
        String ripetizionePassword = ripetiPasswordSignupField.getText().trim();

        try {
            signupService.validaBloccoPasswordSignup(password, ripetizionePassword);
            utenteInCostruzione.setPassword(password);
            signupService.signup(utenteInCostruzione);

            Router.getInstance().navigateTo("home/homePage");
        } catch (Exception e) {
            FocusUtil.setFocusTo(signupContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void handleIndietroDaBloccoPasswordSignup() {
        Router.getInstance().switchBlocks(bloccoPasswordSignup, bloccoCodFiscUsernameSignup);
        FocusUtil.setFocusTo(signupContainer);
    }

    @FXML private void goToLogin() {
        Router.getInstance().loadContent("auth/loginBlock");
    }
}
