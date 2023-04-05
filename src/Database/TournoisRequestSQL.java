package Database;

import java.sql.*;
import java.util.ArrayList;

public abstract class TournoisRequestSQL{
    private static final Connection conn = ConnectionSQL.getConn();

    public static ResultSet getAllTournois(){
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM tournoi");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static ResultSet searchTournois(String entry){
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement("SELECT * FROM tournoi WHERE id = ? or CONCAT(NOM,CODE) LIKE ?");
            statement.setString(1, entry);
            statement.setString(2, '%'+entry+'%');
            rs = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet getEpreuveofTournois(long id){
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement("SELECT * FROM epreuve WHERE id_tournoi = ?");
            statement.setLong(1, id);
            rs = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static boolean addTournoi(String nom, String code){
        PreparedStatement statement = null;
        boolean success = false;
        try{
            statement = conn.prepareStatement("INSERT INTO tournoi(nom, code) VALUES (?,?)");
            statement.setString(1,nom);
            statement.setString(2,code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
    public static boolean deleteTournoi(long ID) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            statement = conn.prepareStatement("DELETE FROM tournoi WHERE id = ?");
            statement.setLong(1, ID);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return success;
    }

    public static boolean modifyTournoi(String name, String code, long id) {
        boolean success = false;

        // Vérification des champs non vides
        if (name.isEmpty() && code.isEmpty()) {
            return success; // si les deux champs sont vides, on ne fait rien et on renvoie false
        }

        String query = "UPDATE tournoi SET ";
        ArrayList<String> fields = new ArrayList<String>();
        if (!name.isEmpty()) {
            fields.add("nom = \"" + name + '"');
        }
        if (!code.isEmpty()) {
            fields.add("code = \"" + code + '"');
        }
        query += String.join(", ", fields) + " WHERE id = " + id;

        System.out.println(query.toString());
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return success;
    }



}
