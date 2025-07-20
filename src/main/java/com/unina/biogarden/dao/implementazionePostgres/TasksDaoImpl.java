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
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.util.DatabaseManager;
import com.unina.biogarden.util.exception.DatabaseException;
import com.unina.biogarden.util.exception.NoDataFound;

public class TasksDaoImpl implements TasksDao {
    @Override
    public ArrayList<Progetto> getProgettiByCodiceFiscale(String codiceFiscale) throws DatabaseException {
        ArrayList<Progetto> lista = new ArrayList<>();
        String sql = "{ call getProgettiProprietario(?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

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

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    int idAttivita = rs.getInt("idAttivita");
                    int idProgetto = rs.getInt("idProgetto");
                    String nomeProgetto = rs.getString("nomeProgetto");
                    String infoColtivatore = rs.getString("infoColtivatore");
                    String idLotto = rs.getString("idLotto");
                    LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                    LocalDate dataFine = (rs.getDate("dataFine") != null) ? rs.getDate("dataFine").toLocalDate() : null;
                    String tipo = rs.getString("tipo");
                    String stato = rs.getString("stato");

                    lista.add(new Attivita(idAttivita, idProgetto, nomeProgetto, infoColtivatore, idLotto, dataInizio, dataFine, tipo, stato));
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

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String idLotto = rs.getString("idLotto");
                    Float mq = rs.getFloat("mq");

                    lista.add(new Lotto(idLotto, mq));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei lotti: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Map<String, Set<String>> getRelazioniProgettoLotto(String codiceFiscale) throws DatabaseException {
        Map<String, Set<String>> progettoLottiMap = new LinkedHashMap<>();

        String sql = "SELECT idProgetto, idLotto FROM getLottiEProgettiPerProprietario(?)";

        try (Connection con = DatabaseManager.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, codiceFiscale);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int idProgettoInt = rs.getInt("idProgetto");
                    String idProgetto;
                    if (rs.wasNull()) {
                        idProgetto = "nessun_progetto";  // chiave speciale per lotti senza progetto
                    } else {
                        idProgetto = String.valueOf(idProgettoInt);
                    }

                    String idLotto = String.valueOf(rs.getInt("idLotto"));

                    progettoLottiMap.computeIfAbsent(idProgetto, _ -> new LinkedHashSet<>()).add(idLotto);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero relazioni progetto-lotto: " + e.getMessage());
        }

        return progettoLottiMap;
    }

    @Override
    public ArrayList<Progetto> getProgettiAttiviProprietario(String codiceFiscale) throws DatabaseException {
        ArrayList<Progetto> lista = new ArrayList<>();
        
        String sql = "{ call getProgettiAttiviProprietario(?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

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
            throw new DatabaseException("Errore durante il recupero dei progetti attivi: " + e);
        }
        return lista;
    }
    @Override
    public List<UtenteColtivatore> getInfoColtivatoriDisponibili() throws DatabaseException {
        List<UtenteColtivatore> coltivatori = new ArrayList<>();
        String sql = "{ call getColtivatoriDisponibili() }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String codFisc = rs.getString("codFisc");
                    String username = rs.getString("username");
                    double saldo = rs.getDouble("saldo");

                    coltivatori.add(new UtenteColtivatore(nome, cognome, codFisc, username, saldo));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei coltivatori disponibili: " + e.getMessage());
        }
        return coltivatori;
    }

    @Override // TODO
    public List<String> getNomiColtureLotto(String idLotto, String idProgetto) throws DatabaseException {
        List<String> colture = new ArrayList<>();
        colture.add("Coltura 1: Pomodori");
        colture.add("Coltura 2: Insalata");
        colture.add("Coltura 3: Pomodori");

        return colture;
    }

    @Override
    public List<ReportVoceLotto> getReportLotti(String codiceFiscale) throws DatabaseException {
        List<ReportVoceLotto> risultati = new ArrayList<>();

        String sql = "SELECT * FROM getReportLottiProprietario(?)";

        try (Connection con = DatabaseManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, codiceFiscale);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idLotto = rs.getInt("idLotto");
                    String tipo = rs.getString("tipo");
                    int numeroRaccolteSuccesso = rs.getInt("numeroRaccolteSuccesso");
                    double mediaKg = rs.getDouble("mediaKg");
                    double minKg = rs.getDouble("minKg");
                    double maxKg = rs.getDouble("maxKg");

                    risultati.add(new ReportVoceLotto(idLotto, tipo, numeroRaccolteSuccesso, mediaKg, minKg, maxKg));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero report lotti: " + e.getMessage());
        }

        return risultati;
    }
  
    @Override
    public void addNewProject(String codFisc, String name, LocalDate startDate, LocalDate endDate) throws DatabaseException {
        String sql = "call creaProgetto(?, ?, ?, ?)";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setString(1, codFisc);
            stmt.setString(2, name);
            stmt.setDate(3, java.sql.Date.valueOf(startDate));
            stmt.setDate(4, java.sql.Date.valueOf(endDate));

            stmt.execute();

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante la creazione del progetto: " + e.getMessage());
        }
    }
    @Override // TODO
    public void addNewActivity(String idProgetto, String idLotto, String idColtivatore, String tipo, String stato, LocalDate activityStartDate, String tipoSemina, String idColtura, String raccoltaQuantitaPrevista) throws DatabaseException {
        String sql = "CALL creaAttivita(?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(activityStartDate));
            stmt.setString(2, tipo);
            stmt.setString(3, stato);
            stmt.setString(4, idColtivatore);
            stmt.setInt(5, Integer.parseInt(idLotto));
            stmt.setInt(6, Integer.parseInt(idProgetto));

            stmt.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Errore nella creazione dell'attività: " + e.getMessage());
        }
    }

    @Override
    public Progetto getProgettoByAttivita(int idAttivita) throws DatabaseException {
        String sql = "{ CALL getProgettoByAttivita(?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idAttivita);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("idProgetto");
                    String nome = rs.getString("nome");
                    LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                    LocalDate dataFine = rs.getDate("dataFine").toLocalDate();
                    String codFisc = rs.getString("codFisc");

                    Progetto progetto = new Progetto(String.valueOf(id), nome, dataInizio, dataFine, codFisc);
                    return progetto;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore: " + e.getMessage());
        }
    }
    @Override
    public Lotto getLottoByAttivita(int idAttivita) throws DatabaseException {
        String sql = "{ CALL getLottoByAttivita(?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idAttivita);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("idLotto");
                    float mq = rs.getFloat("mq");

                    Lotto lotto = new Lotto(String.valueOf(id), mq);
                    return lotto;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore: " + e.getMessage());
        }
    }
    @Override
    public UtenteColtivatore getColtivatoreByAttivita(int idAttivita) throws DatabaseException {
        String sql = "{ CALL getColtivatoreByAttivita(?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idAttivita);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String codFisc = rs.getString("codFisc");
                    String username = rs.getString("username");
                    double saldo = rs.getDouble("saldo");

                    UtenteColtivatore coltivatore = new UtenteColtivatore(nome, cognome, codFisc, username, saldo);
                    return coltivatore;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore: " + e.getMessage());
        }
    }

    @Override // TODO
    public void updateActivity(String idAttivita, String idProgetto, String idLotto, String codFisc, String tipo, String stato, LocalDate dataInizio, LocalDate dataFine) throws DatabaseException {
        String sql = "{ CALL updateAttivita(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection con = DatabaseManager.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            int idAtt = Integer.parseInt(idAttivita);
            int idProj = Integer.parseInt(idProgetto);
            int idLot = Integer.parseInt(idLotto);

            cs.setInt(1, idAtt);
            cs.setInt(2, idProj);
            cs.setInt(3, idLot);
            cs.setString(4, codFisc);
            cs.setString(5, tipo);
            cs.setString(6, stato);
            cs.setDate(7, java.sql.Date.valueOf(dataInizio));
            if (dataFine != null) {
                cs.setDate(8, java.sql.Date.valueOf(dataFine));
            } else {
                cs.setNull(8, java.sql.Types.DATE);
            }
            
            cs.executeUpdate();
        } catch (Exception e) { // SQLException e NumberFormatException
            throw new DatabaseException("Errore aggiornamento attività: " + e.getMessage());
        }
    }
}
