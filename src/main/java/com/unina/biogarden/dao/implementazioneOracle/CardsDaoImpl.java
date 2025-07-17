package com.unina.biogarden.dao.implementazioneOracle;

import java.time.LocalDate;
import java.util.*;

import com.unina.biogarden.dao.CardsDao;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.exception.DatabaseException;

public class CardsDaoImpl implements CardsDao {
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
    public void addActivity(String name, LocalDate startDate, LocalDate endDate) throws DatabaseException {}
}
