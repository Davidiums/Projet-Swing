package Pages.Tournois;


import Pages.Matches.AddMatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

import static Database.EpreuveRequestSQL.deleteEpreuve;
import static Database.EpreuveRequestSQL.getAllEpreuveForTournoiAtYear;
import static Database.MatchRequestSQL.deleteMatch;
import static Database.MatchRequestSQL.getMatchforTournoi;
import static Database.TournoisRequestSQL.*;
import static Database.TournoisRequestSQL.getAllTournois;
import static StaticMethode.MethodeJtable.*;
import static StaticMethode.TableModel.*;

public class TournoisMain extends JFrame{
    private JTable tableTournois;
    private JButton ajouterUnTournoisButton;
    private JButton modifierLeTournoiButton;
    private JButton supprimerLeTournoiButton;
    private JPanel mainPanel;
    private JComboBox choixAnnee;
    private JPanel  matchPanel;
    private JButton addEpreuve;
    private JTable matchTable;
    private JTextField tournoiTextResearch;
    private JButton rechercherButton;
    private JLabel errorArea;
    private JTable epreuveTable;
    private JButton definirEpreuveButton;
    private JLabel toDifineLabel;
    private JButton supprimerLEpreuveButton;
    private JButton supprimerLeMatchButton;

    private Long currentYears;

    private Object currentSelection;

    public TournoisMain() {
        hideTournoiButton();
        initJtable(tableTournois);
        remplirTournoisTable(getAllTournois());
        tableTournois.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initJtable(matchTable);
        matchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initJtable(epreuveTable);
        epreuveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDifineLabel.setVisible(false);

        ajouterUnTournoisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        tableTournois.getSelectionModel().addListSelectionListener(e -> {
            this.currentSelection = choixAnnee.getSelectedItem();
            refreshYearSelection();
        });


        ajouterUnTournoisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTournoi add = new AddTournoi(TournoisMain.this);
            }
        });


        modifierLeTournoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        supprimerLeTournoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTournoi(getSelectedID());
                tableTournois.clearSelection();
                refreshTournoiTab();
                refreshYearSelection();
            }
        });


        choixAnnee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = choixAnnee.getSelectedItem();
                if (selectedItem != null) {
                    try {
                        long selectedText = Long.parseLong(selectedItem.toString());
                        currentYears = selectedText;
                        if (selectedText > 1500) refreshMatchTab(selectedText);
                    }catch (Exception ex){
                        System.out.println(ex.toString());
                    }
                }
            }
        });


        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplirTournoisTable(searchTournois(tournoiTextResearch.getText()));
            }
        });


        modifierLeTournoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyTournoi modify = new ModifyTournoi(TournoisMain.this, getSelectedID(), getSelectedName());
            }
        });


        addEpreuve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEpreuve add = new AddEpreuve(TournoisMain.this, getSelectedID(), getSelectedName());
            }
        });


        definirEpreuveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMatch page = new AddMatch(TournoisMain.this, getEpreuveID(), getEpreuveSexe());
            }
        });
        supprimerLEpreuveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEpreuve(getEpreuveID());
                refreshYearSelection();
            }
        });
        supprimerLeMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMatch(getMatchID());
                refreshMatchTab(currentYears);
            }
        });
    }

    private long getMatchID() {
        int selectedRow = matchTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (Long) matchTable.getModel().getValueAt(selectedRow, 4);
    }

    private void showTournoiButton() {
        modifierLeTournoiButton.setEnabled(true);
        modifierLeTournoiButton.setVisible(true);
        supprimerLeTournoiButton.setEnabled(true);
        supprimerLeTournoiButton.setVisible(true);
        addEpreuve.setEnabled(true);
        addEpreuve.setVisible(true);
        supprimerLeTournoiButton.setVisible(true);
        supprimerLEpreuveButton.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    private long getSelectedID(){
        int selectedRow = tableTournois.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (Long) tableTournois.getModel().getValueAt(selectedRow, 0);
    }

    private long getEpreuveID(){
        int selectedRow = epreuveTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (Long) epreuveTable.getModel().getValueAt(selectedRow, 0);
    }

    private String getEpreuveSexe(){
        int selectedRow = epreuveTable.getSelectedRow();
        if (selectedRow == -1) {
            return "N";
        }
        return (String) epreuveTable.getModel().getValueAt(selectedRow, 2);
    }
    private String getSelectedName(){
        return (String) tableTournois.getModel().getValueAt(tableTournois.getSelectedRow(), 1);
    }
    private void remplirTableauMatch(ResultSet rs){
        remplirJTable(matchTable, matchTable(), rs);
    }

    private void remplirTableauEpreuve(ResultSet rs){
        remplirJTable(epreuveTable, epreuveForTournoiTable(),rs );
    }

    public void refreshMatchTab(long year){
        ResultSet rs = getMatchforTournoi(year,getSelectedID());
                remplirTableauEpreuve(getAllEpreuveForTournoiAtYear(getSelectedID(),year));
        try {
            if(!rs.next()){
                toDifineLabel.setVisible(true);
            }else{
                toDifineLabel.setVisible(false);
                rs.previous();
            }
                remplirTableauMatch(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void remplirTournoisTable(ResultSet rs){
        remplirJTable(tableTournois,tournoisTable(),rs);
    }
    public void refreshTournoiTab(){
        remplirTournoisTable(getAllTournois());
    }

    private void hideTournoiButton(){
        modifierLeTournoiButton.setEnabled(false);
        modifierLeTournoiButton.setVisible(false);
        supprimerLeTournoiButton.setEnabled(false);
        supprimerLeTournoiButton.setVisible(false);
        addEpreuve.setEnabled(false);
        addEpreuve.setVisible(false);
        supprimerLeTournoiButton.setVisible(false);
        supprimerLEpreuveButton.setVisible(false);
    }
    public void setErrorArea(String errorMessage){
        errorArea.setText(errorMessage);
    }

    public void refreshYearSelection(){
        ResultSet rs = getEpreuveofTournois(getSelectedID());
        TreeSet<String> yearsSet = new TreeSet<>();
        if(getSelectedID()!= -1){
            showTournoiButton();
        }else{
            hideTournoiButton();
        }
        try {
            while (rs.next()) {
                String date = rs.getString("annee");
//                    String year = date.substring(0, 4);
                yearsSet.add(date);
            }
        } catch (SQLException ef) {
            throw new RuntimeException(ef);
        }
        choixAnnee.removeAllItems();
        for (String year : yearsSet) {
            choixAnnee.addItem(year);
        }
        if (currentSelection != null) {
            choixAnnee.setSelectedItem(currentSelection);
        }else{

        }
    }
}
