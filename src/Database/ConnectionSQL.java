package Database;

import Utilitaire.Color;

import java.sql.*;
public abstract class ConnectionSQL {
    private static Connection conn = null;

    public static boolean initCo() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris", "root", "");
            System.out.println(Color.GREEN + "Success!" + Color.RESET);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public static Connection getConn() {
        return conn;
    }
}
