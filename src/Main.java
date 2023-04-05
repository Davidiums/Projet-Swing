import Database.ConnectionSQL;
import Pages.Homepage;

import java.sql.Connection;

public class Main {
    public static void main(String[] args){
        Homepage h = new Homepage();
        h.start();
    }
}