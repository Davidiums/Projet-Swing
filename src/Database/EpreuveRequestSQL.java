package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EpreuveRequestSQL {
    private static final Connection conn = ConnectionSQL.getConn();
    public static boolean addEpreuveNotMixte(int annee, String sexe,long idTournoi){
        PreparedStatement statement = null;
        int nbModif = 0;
        try{
            statement = conn.prepareStatement("INSERT INTO epreuve(ANNEE, TYPE_EPREUVE, ID_TOURNOI) VALUES (?,?,?)");
            statement.setInt(1,annee);
            statement.setString(2, sexe);
            statement.setLong(3, idTournoi);
            nbModif=statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbModif>0;
    }

    public static boolean addEpreuveMixte(int annee, long idTournoi){
        PreparedStatement statement = null;
        int nbModif = 0;
        try{
            statement = conn.prepareStatement("INSERT INTO epreuve(ANNEE, TYPE_EPREUVE, ID_TOURNOI) VALUES (?,?,?)");
            statement.setInt(1,annee);
            statement.setLong(3, idTournoi);
            statement.setString(2,"H");
            nbModif=statement.executeUpdate();
            statement.setString(2, "F");
            nbModif+=statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbModif>0;
    }

    public static ResultSet getAllEpreuveForTournoiAtYear(long id, long year){
        PreparedStatement statement = null;
        ResultSet rs = null;
        System.out.println(id);
        System.out.println(year);
        try{
            statement = conn.prepareStatement("SELECT * FROM epreuve WHERE ID_TOURNOI = ? AND ANNEE = ?");
            statement.setLong(1, id);
            statement.setLong(2,year);
            rs = statement.executeQuery();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static boolean deleteEpreuve(long id) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM epreuve WHERE id = ?");
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
