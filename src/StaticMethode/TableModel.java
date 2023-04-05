package StaticMethode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class TableModel {

    public static DefaultTableModel playerTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "NOM", "PRENOM", "SEXE"}, 0){

        };
        return model;
    }

    public static DefaultTableModel tournoisTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "NOM", "CODE"}, 0){

        };
        return model;
    }

    public static DefaultTableModel matchTable(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"Sexe","Vainqueur", "Finaliste","ID epreuve", "id match"},0){

        };
        return model;
    }

    public static DefaultTableModel epreuveForTournoiTable(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Année", "Type"},0){

        };
        return model;
    }

    public static DefaultTableModel allMatchbyPlayerTable(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Tournoi", "Année"},0){

        };
        return model;
    }


}
