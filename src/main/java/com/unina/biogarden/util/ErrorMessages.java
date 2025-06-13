package com.unina.biogarden.util;

public enum ErrorMessages {
    ERRORE_GENERICO("Si è verificato un errore, riprova più tardi."),
    CREDENZIALI_NON_VALIDE("Credenziali non valide."),
    NOME_VUOTO("Devi inserire un nome per continuare."),
    COGNOME_VUOTO("Devi inserire un cognome per continuare."),
    CODICE_FISCALE_VUOTO("Devi inserire un codice fiscale per continuare."),
    CODICE_FISCALE_LUNGHEZZA("Il codice fiscale deve contenere 16 caratteri."),
    CODICE_FISCALE_FORMATO("Il codice fiscale può contenere solo lettere e numeri."),
    USERNAME_VUOTO("Devi inserire uno username per continuare."),
    USERNAME_FORMATO("Lo username può contenere solo lettere, numeri, punti, trattini bassi e trattini."),
    USERNAME_OCCUPATO("L'username inserito è già in uso."),
    PASSWORD_VUOTA("Devi inserire una password per continuare."),
    PASSWORD_LUNGHEZZA("La password deve contenere almeno 8 caratteri."),
    PASSWORD_SPAZI("La password non può contenere spazi."),
    PASSWORD_LETTERA("La password deve contenere almeno una lettera."),
    PASSWORD_NUMERO("La password deve contenere almeno un numero."),
    PASSWORD_CARATTERE_SPECIALE("La password deve contenere almeno un carattere speciale."),
    PASSWORD_RIPETUTA_VUOTA("Devi ripetere la password per continuare."),
    PASSWORD_NON_COINCIDONO("Le password non coincidono.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
