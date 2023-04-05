package Database;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public abstract class PlayerRequestSQL{
    private static final Connection conn = ConnectionSQL.getConn();

    public static ResultSet getAllPlayer(){
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM joueur");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static ResultSet getAllGirl(){
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM joueur WHERE SEXE = 'F' ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static ResultSet getAllMen(){
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM joueur WHERE SEXE = 'H' ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static boolean deletePlayers(List<Long> ids) {
        PreparedStatement statement = null;
        boolean success = true;

        try {
            conn.setAutoCommit(false); // Démarrer une transaction

            statement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID = ?");

            for (Long id : ids) {
                statement.setLong(1, id);
                statement.addBatch(); // Ajouter la requête au batch statement
            }

            int[] updateCounts = statement.executeBatch(); // Exécuter le batch statement

            for (int updateCount : updateCounts) {
                if (updateCount != 1) { // Si le nombre de lignes supprimées n'est pas égal à 1
                    success = false;
                    break;
                }
            }

            if (success) {
                conn.commit(); // Valider la transaction
            } else {
                conn.rollback(); // Annuler la transaction
            }

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;

            try {
                conn.rollback(); // Annuler la transaction en cas d'erreur
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true); // Rétablir le mode de validation automatique des transactions
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close(); // Fermer le statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }



    public static boolean addPlayer(String firstName, String lastName, String sexe) {
        PreparedStatement statement = null;
        boolean success = false;
        try {
            statement = conn.prepareStatement("INSERT INTO JOUEUR (NOM, PRENOM, SEXE) VALUES (?, ?, ?)");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, sexe);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
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

    public static ResultSet searchPlayer(String name) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement("SELECT * FROM JOUEUR WHERE CONCAT(NOM, ' ',PRENOM)= ? OR CONCAT(PRENOM, ' ',NOM)= ? OR PRENOM = ? OR NOM = ? OR ID = ?");
            for (int i = 1; i < 6; i++) {
                statement.setString(i, name);
            }
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean updatePlayer(Long ID, String firstName, String lastName, String sexe) {
        PreparedStatement statement = null;
        try {
            if (!sexe.equals("noModify")) {
                statement = conn.prepareStatement("UPDATE JOUEUR SET NOM = ?, PRENOM = ?, SEXE = ? WHERE ID = ?");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, sexe);
                statement.setLong(4, ID);
            } else {
                statement = conn.prepareStatement("UPDATE JOUEUR SET NOM = ?, PRENOM = ? WHERE ID = ?");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setLong(3, ID);
            }
            int result = statement.executeUpdate();
            return (result > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
