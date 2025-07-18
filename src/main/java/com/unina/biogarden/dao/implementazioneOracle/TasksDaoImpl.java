package com.unina.biogarden.dao.implementazioneOracle;

import java.time.LocalDate;
import java.util.*;

import com.unina.biogarden.dao.TasksDao;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.ReportVoceLotto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.exception.DatabaseException;

public class TasksDaoImpl implements TasksDao {
    @Override
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Progetto> lista = new ArrayList<>();

        lista.add(new Progetto(
            "Progetto Orto Urbano",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 10, 31),
            "In corso"
        ));

        lista.add(new Progetto(
            "Progetto Compostaggio",
            LocalDate.of(2025, 3, 15),
            LocalDate.of(2025, 8, 15),
            "Completata"
        ));

        lista.add(new Progetto(
            "Progetto Lorem Ipsum",
            LocalDate.of(2025, 3, 15),
            LocalDate.of(2025, 8, 15),
            "Completata"
        ));

        lista.add(new Progetto(
            "Progetto Lulu",
            LocalDate.of(2025, 3, 15),
            LocalDate.of(2025, 8, 15),
            "TERMINATO"
        ));

        lista.add(new Progetto(
            "Progetto Sort",
            LocalDate.of(2025, 3, 15),
            LocalDate.of(2025, 8, 15),
            "Completata"
        ));

        return lista;
    }
    @Override
    public ArrayList<Attivita> getAttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Attivita> lista = new ArrayList<>();

        lista.add(new Attivita(
        "Irrigazione estiva",
        "Mario Rossi",
        "LOT123",
        LocalDate.of(2025, 6, 10),
        null,
        "Irrigazione",
        "Completata"
        ));

        lista.add(new Attivita(
        "Raccolta pomodori",
        "Anna Verdi",
        "LOT456",
        LocalDate.of(2025, 7, 1),
        LocalDate.of(2025, 7, 3),
        "Raccolta",
        "In corso"
        ));

        lista.add(new Attivita(
        "Pulizia appezzamento",
        "Luigi Bianchi",
        "LOT789",
        LocalDate.of(2025, 7, 5),
        LocalDate.of(2025, 7, 10),
        "Manutenzione",
        "Pianificata"
        ));

        lista.add(new Attivita(
        "Irrigazione estiva",
        "Mario Rossi",
        "LOT123",
        LocalDate.of(2025, 6, 10),
        LocalDate.of(2025, 6, 20),
        "Irrigazione",
        "Completata"
        ));

        lista.add(new Attivita(
        "Raccolta pomodori",
        "Anna Verdi",
        "LOT456",
        LocalDate.of(2025, 7, 1),
        LocalDate.of(2025, 7, 3),
        "Raccolta",
        "In corso"
        ));

        lista.add(new Attivita(
        "Pulizia appezzamento",
        "Luigi Bianchi",
        "LOT789",
        LocalDate.of(2025, 7, 5),
        LocalDate.of(2025, 7, 10),
        "Manutenzione",
        "Pianificata"
        ));

        return lista;
    }
    @Override
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Lotto> lista = new ArrayList<>();

        lista.add(new Lotto(
        "1",
        19.4f,
        "Progetto Orto Urbano"
        ));

        lista.add(new Lotto(
        "25",
        25.0f,
        "PRO456"
        ));

        lista.add(new Lotto(
            "3151",
            19.4f,
            "Progetto Orto Urbano"
        ));

        lista.add(new Lotto(
            "2",
            25.0f,
            "PRO456"
        ));

        lista.add(new Lotto(
            "35",
            19.4f,
            "PRO123"
        ));

        lista.add(new Lotto(
            "531",
            25.0f,
            "PRO456"
        ));

        lista.add(new Lotto(
            "4",
            19.4f,
            "Progetto Orto Urbano"
        ));

        lista.add(new Lotto(
            "31353",
            25.0f,
            "PRO456"
        ));

        lista.add(new Lotto(
            "634",
            19.4f,
            "PRO123"
        ));

        return lista;
    }

    @Override
    public void addProject(String name, LocalDate startDate, LocalDate endDate) throws DatabaseException {}
    @Override
    public void addNewActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, String tipoSemina, String idColtura, String raccoltaQuantitaPrevista) throws DatabaseException {}

    @Override
    public List<String> getNomiProgettiProprietario(String idProprietario) throws DatabaseException {
        List<String> progetti = new ArrayList<>();
        progetti.add("1");
        progetti.add("Progetto 2");
        progetti.add("Progetto 3");
        return progetti;
    }
    @Override
    public List<String> getNomiLottiProprietario(String idProprietario) throws DatabaseException {
        List<String> lotti = new ArrayList<>();
        lotti.add("1");
        lotti.add("Lotto numero 2");
        lotti.add("Lotto numero 3");
        
        return lotti;
    }
    @Override
    public List<String> getInfoColtivatoriDisponibili() throws DatabaseException {
        List<String> coltivatori = new ArrayList<>();
        coltivatori.add("Luca Guida, CODFISC");
        coltivatori.add("Mario Rosa, CODFISC");
        coltivatori.add("Giulia Fontanella, CODFISC");
        return coltivatori;
    }

    @Override
    public List<String> getNomiColtureLotto(String idLotto, String idProgetto) throws DatabaseException {
        List<String> colture = new ArrayList<>();
        colture.add("Coltura 1: Pomodori");
        colture.add("Coltura 2: Insalata");
        colture.add("Coltura 3: Pomodori");

        return colture;
    }

    @Override
    public List<ReportVoceLotto> getReportLotti(String codiceFiscale) throws DatabaseException {
        List<ReportVoceLotto> datiDiTest = List.of(
            new ReportVoceLotto(1, "Erbe arom.", 4, 25.0, 20.5, 29.0),
            new ReportVoceLotto(1, "Insalata", 1, 25.0, 20.5, 29.0),
            new ReportVoceLotto(1, "Pomodori", 5, 25.0, 20.5, 29.0),
            new ReportVoceLotto(2, "Pomodori", 1, 25.0, 20.5, 29.0)
        );

        return datiDiTest;
    }

    @Override
    public void updateActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, LocalDate activityEndDate) throws DatabaseException {}
}
