package com.unina.biogarden.dao.implementazioneOracle;

import com.unina.biogarden.util.exception.InvalidCredentialsException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.unina.biogarden.dao.UtenteDao;
import com.unina.biogarden.model.Utente;
import com.unina.biogarden.model.UtenteColtivatore;
import com.unina.biogarden.model.UtenteProprietario;
import com.unina.biogarden.util.DatabaseManager;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.exception.DatabaseException;

public class UtenteDaoImpl implements UtenteDao {
    @Override
    public Utente verificaCredenziali(String username, String password) throws InvalidCredentialsException, DatabaseException {
        String sql = "SELECT * FROM loginUtente(?, ?)";

        try (Connection connection = DatabaseManager.getConnection(); // Nella parentesi tonda va tutto ciò che poi andrebbe chiuso nel finally, in tal modo questo viene fatto in automatico
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String codFisc = rs.getString("codFisc");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String usernameDB = rs.getString("username");
                    String tipoUtente = rs.getString("tipoutente");

                    if ("Proprietario".equalsIgnoreCase(tipoUtente)) {
                        String partitaIva = rs.getString("partitaIVA");
                        return new UtenteProprietario(nome, cognome, codFisc, usernameDB, partitaIva);
                    } else if ("Coltivatore".equalsIgnoreCase(tipoUtente)) {
                        double saldo = rs.getDouble("saldo");
                        return new UtenteColtivatore(nome, cognome, codFisc, usernameDB, saldo);
                    } else {
                        throw new InvalidCredentialsException(ErrorMessage.CREDENZIALI_NON_VALIDE);
                    }
                } else {
                    throw new InvalidCredentialsException(ErrorMessage.CREDENZIALI_NON_VALIDE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Per debug dettagliato
            throw new DatabaseException(ErrorMessage.ERRORE_GENERICO);
        }
    }

    @Override
    public boolean isUsernameLibero(String username) throws DatabaseException {
        String sql = "SELECT controllaUnicitaUsername(?) AS is_libero";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_libero");
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(ErrorMessage.USERNAME_OCCUPATO);
        }
    }
    @Override
    public boolean isCodFiscLibero(String codFisc) throws DatabaseException {
        String sql = "SELECT controllaUnicitaCodFisc(?) AS is_libero";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codFisc);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_libero");
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(ErrorMessage.USERNAME_OCCUPATO);
        }
    }

    @Override
    public void registraUtente(Utente utente) throws DatabaseException {
        String sql = "CALL registraNuovoUtente(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, utente.getCodiceFiscale());
            cs.setString(2, utente.getNome());
            cs.setString(3, utente.getCognome());
            cs.setString(4, utente.getPassword());
            cs.setString(5, utente.getUsername());

            if (utente instanceof UtenteProprietario) {
                UtenteProprietario p = (UtenteProprietario) utente;
                cs.setString(6, p.getPartitaIva());
            } else {
                cs.setNull(6, java.sql.Types.VARCHAR);
            }

            cs.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante la registrazione dell'utente: " + e.getMessage());
        }
    }
}
