package Database;

import com.mysql.cj.xdevapi.DeleteStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class MatchRequestSQL {
    private static final Connection conn = ConnectionSQL.getConn();

    public static ResultSet getMatchforTournoi(long year, long epreuveID){
        ResultSet rs = null;
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement("Select winner.SEXE, CONCAT(winner.PRENOM, ' ', winner.NOM) AS Vainqueur, CONCAT(looser.PRENOM,' ', looser.NOM) AS Finaliste, ID_EPREUVE, match_tennis.ID as 'id match' from match_tennis \n" +
                    "INNER JOIN joueur winner ON match_tennis.ID_VAINQUEUR = winner.ID\n" +
                    "INNER JOIN joueur looser ON match_tennis.ID_FINALISTE = looser.ID\n" +
                    "INNER JOIN epreuve on match_tennis.ID_EPREUVE = epreuve.ID\n" +
                    "INNER JOIN tournoi ON epreuve.ID_TOURNOI = tournoi.ID\n" +
                    "WHERE tournoi.ID = ? AND epreuve.ANNEE = ? ");
            statement.setLong(1, epreuveID);
            statement.setLong(2, year);
            rs = statement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    };

    public static boolean addMatch(long idEpreuve, long idVainqueur, long idFinaliste) {
        boolean success = false;
        PreparedStatement statement = null;
        String query = "INSERT INTO match_tennis (id_epreuve, id_vainqueur, id_finaliste) VALUES (?, ?, ?)";
        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, idEpreuve);
            statement.setLong(2, idVainqueur);
            statement.setLong(3, idFinaliste);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    public static boolean updateMatch(long idMatch, long idVainqueur, long idFinaliste) {
        boolean success = false;
        PreparedStatement statement = null;
        String query = "UPDATE match_tennis SET id_Vainqueur = ?, id_Finaliste = ? WHERE id_Match = ?";
        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, idVainqueur);
            statement.setLong(2, idFinaliste);
            statement.setLong(3, idMatch);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    public static boolean deleteMatch(long idMatch){
        boolean success = false;
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM match_tennis where id = ?");
            statement.setLong(1,idMatch);
            success =  statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    public static ResultSet showPlayersMatchs(List<Long> liste){
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT match_tennis.ID, tournoi.NOM, epreuve.ANNEE FROM match_tennis" +
                " INNER JOIN joueur ON match_tennis.id_vainqueur = joueur.id OR match_tennis.id_finaliste = joueur.id" +
                " INNER JOIN epreuve ON match_tennis.id_epreuve = epreuve.id" +
                " INNER JOIN tournoi ON epreuve.id_tournoi = tournoi.id"+
                " WHERE joueur.ID =?";


        if (liste.size()>1){
            for (int i = 1; i < liste.size(); i++) {
            query += " OR joueur.ID = ?";
            }
        }
        try {
            statement = conn.prepareStatement(query);
            for (int i = 0; i < liste.size(); i++) {
                statement.setLong(i+1, liste.get(i));
            }
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean deletePlayersMatch(List<Long> liste){
        boolean success =false;
        PreparedStatement statement = null;
        String query =  "DELETE FROM match_tennis "+
                        " INNER JOIN joueur ON match_tennis.id_vainqueur = joueur.id OR match_tennis.id_finaliste = joueur.id" +
                        " WHERE joueur.ID =?";
        if (liste.size()>1){
            for (int i = 1; i < liste.size(); i++) {
                query += " OR joueur.ID = ?";
            }
        }
        try {
            statement = conn.prepareStatement(query);
            for (int i = 0; i < liste.size(); i++) {
                statement.setLong(i+1, liste.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
