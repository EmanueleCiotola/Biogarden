package com.unina.biogarden.service;

import com.unina.biogarden.dao.utente.UtenteDao;
import com.unina.biogarden.dao.utente.UtenteDaoImpl;
import com.unina.biogarden.dto.Utente;
import com.unina.biogarden.gui.controller.SnackbarController;
import com.unina.biogarden.router.Router;

public class SigninService {
    private UtenteDao dao = new UtenteDaoImpl();

    public Utente signin(String username, String password) {
        if (username.isEmpty()) {
            SnackbarController.show("Devi inserire un'email per continuare.");
        } else if (password.isEmpty()) {
            SnackbarController.show("Devi inserire una password per continuare.");
        } else if (dao.verificaCredenziali(username, password) != null) {
            Router.getInstance().navigateTo("homePage");
        } else {
            SnackbarController.show("Credenziali non valide.");
        }
        return null;
    }
    
    public Boolean isPrimoBloccoValido(String nome, String cognome) {
        if (nome.isEmpty()) {
            SnackbarController.show("Devi inserire un nome per continuare.");
        } else if (cognome.isEmpty()) {
            SnackbarController.show("Devi inserire un cognome per continuare.");
        } else {
            return true;
        }
        return false;
    }
    
    public Boolean isSecondoBloccoValido(String codiceFiscale, String username) {
        if (!isCodiceFiscaleValido(codiceFiscale)) return false;
        else if (!isUsernameValido(username)) return false;
        return true;
    }
    private boolean isCodiceFiscaleValido(String codiceFiscale) {
        if (codiceFiscale.isEmpty()) {
            SnackbarController.show("Il codice fiscale non può essere vuoto.");
        } else if (codiceFiscale.length() != 16) {
            SnackbarController.show("Il codice fiscale deve contenere 16 caratteri.");
        } else if (!codiceFiscale.matches("^[A-Z0-9]{16}$")) {
            SnackbarController.show("Il codice fiscale può contenere solo lettere e numeri.");
        } else return true;
        return false;
    }
    private boolean isUsernameValido(String username) {
        if (username.isEmpty()) {
            SnackbarController.show("Devi inserire uno username per continuare.");
        } else if (!username.matches("^[\\p{L}0-9._-]+$")) {
            SnackbarController.show("Lo username può contenere solo lettere, numeri, punti, trattini bassi e trattini.");
        } else if (!dao.isUsernameLibero(username)) {
            SnackbarController.show("L'username inserito è già in uso.");
        } else return true;
        return false;
    }

    
}
