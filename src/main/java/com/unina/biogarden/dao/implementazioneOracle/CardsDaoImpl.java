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

        return lista;
    }

    @Override
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Lotto> lista = new ArrayList<>();

        lista.add(new Lotto(
        "LOT100",
        19.4f
        ));

        lista.add(new Lotto(
        "LOT200",
        25.0f
        ));

        return lista;
    }
}
