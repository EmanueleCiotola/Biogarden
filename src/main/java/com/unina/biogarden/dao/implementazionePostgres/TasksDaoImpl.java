package com.unina.biogarden.dao.implementazionePostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.unina.biogarden.dao.TasksDao;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.model.ReportVoceLotto;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.DatabaseManager;
import com.unina.biogarden.util.exception.DatabaseException;

public class TasksDaoImpl implements TasksDao {
    @Override
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Progetto> lista = new ArrayList<>();
        String sql = "{ call getProgettiProprietario(?) }";

        try (Connection conn = DatabaseManager.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    int idProgetto = rs.getInt("idProgetto");
                    String nomeProgetto = rs.getString("nomeProgetto");
                    LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                    LocalDate dataFine = rs.getDate("dataFine").toLocalDate();
                    String stato = rs.getString("stato");

                    lista.add(new Progetto(String.valueOf(idProgetto), nomeProgetto, dataInizio, dataFine, stato));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei progetti: " + e);
        }
        return lista;
    }
    @Override
    public ArrayList<Attivita> getAttivitaByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Attivita> lista = new ArrayList<>();
        String sql = "{ call getAttivitaProprietario(?) }";

        try (Connection conn = DatabaseManager.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String nomeProgetto = rs.getString("nomeProgetto");
                    String infoColtivatore = rs.getString("infoColtivatore");
                    String idLotto = rs.getString("idLotto");
                    LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                    LocalDate dataFine = (rs.getDate("dataFine") != null) ? rs.getDate("dataFine").toLocalDate() : null;
                    String tipo = rs.getString("tipo");
                    String stato = rs.getString("stato");

                    lista.add(new Attivita(nomeProgetto, infoColtivatore, idLotto, dataInizio, dataFine, tipo, stato));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero delle attività: " + e.getMessage());
        }
        return lista;
    }
    @Override
    public ArrayList<Lotto> getLottiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Lotto> lista = new ArrayList<>();
        String sql = "{ call getLottiProprietario(?) }";

        try (Connection conn = DatabaseManager.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String idLotto = rs.getString("idLotto");
                    Float mq = rs.getFloat("mq");
                    String idProgetto = rs.getString("idProgetto");

                    lista.add(new Lotto(idLotto, mq, idProgetto));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei lotti: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void addNewProject(String name, LocalDate startDate, LocalDate endDate) throws DatabaseException {
        String sql = "call creaProgetto(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));

            stmt.execute();

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante la creazione del progetto: " + e.getMessage());
        }
    }
    @Override
    public void addNewActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, String tipoSemina, String idColtura, String raccoltaQuantitaPrevista) throws DatabaseException {}

    @Override
    public List<String> getNomiProgettiAttiviProprietario(String idProprietario) throws DatabaseException {
        List<String> progetti = new ArrayList<>();
        progetti.add("Progetto 1");
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
