package com.unina.biogarden.util;

public enum ErrorMessage {
    ERRORE_GENERICO("Si è verificato un errore, riprova più tardi."),
    ERRORE_GENERICO_SERVER("Si è verificato un problema con il server, riprova più tardi."),
    COLTIVATORE_LOGIN("L'utente che ha effettuato l'accesso è un coltivatore. Non valido."),
    COLTIVATORE_SIGNIN("L'utente che ha effettuato la registrazione è un coltivatore. Non valido."),
    CREDENZIALI_NON_VALIDE("Credenziali non valide."),
    PIVA_VUOTA("Devi inserire una partita IVA per continuare."),
    PIVA_LUNGHEZZA("La partita IVA deve contenere 11 caratteri."),
    PIVA_FORMATO("La partita IVA può contenere solo numeri."),
    NOME_VUOTO("Devi inserire un nome per continuare."),
    COGNOME_VUOTO("Devi inserire un cognome per continuare."),
    CODICE_FISCALE_VUOTO("Devi inserire un codice fiscale per continuare."),
    CODICE_FISCALE_LUNGHEZZA("Il codice fiscale deve contenere 16 caratteri."),
    CODICE_FISCALE_FORMATO("Il codice fiscale può contenere solo lettere e numeri."),
    CODICE_FISCALE_OCCUPATO("Il codice fiscale inserito è già in uso."),
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
    PASSWORD_NON_COINCIDONO("Le password non coincidono."),
    CARICAMENTO_PROGETTI("Errore nel caricamento dei progetti. Riprova più tardi."),
    CARICAMENTO_ATTIVITA("Errore nel caricamento delle attività. Riprova più tardi."),
    CARICAMENTO_LOTTI("Errore nel caricamento dei lotti. Riprova più tardi."),
    DATA_INIZIO_NULLA("Devi inserire una data di inizio per continuare."),
    DATA_FINE_NULLA("Devi inserire una data di fine per continuare."),
    DATA_FINE_PRECEDE_DATA_INIZIO("La data di fine non può precedere la data di inizio."),
    NESSUN_PROGETTO_TROVATo("Non hai nessun progetto."),
    NESSUN_LOTTO_TROVATO("Non hai nessun lotto."),
    NESSUNA_COLTURA_TROVATA("Non sono state trovate colture su questo lotto in questo progetto."),
    PROGETTO_NON_TROVATO("Non è stato trovato il progetto a cui appartiene questa attività."),
    LOTTO_NON_TROVATO("Non è stato trovato il lotto in cui è stata svolta questa attività."),
    COLTIVATORE_NON_TROVATO("Non è stato trovato il coltivatore che ha svolto questa attività."),
    ID_COLTURA_RICHIESTO_PER_TIPO_ATTIVITA("Quest'attività deve essere associata a una coltura esistente.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
