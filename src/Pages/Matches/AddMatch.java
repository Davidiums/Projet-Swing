package Pages.Matches;

import Database.PlayerRequestSQL;
import Pages.Tournois.TournoisMain;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Database.MatchRequestSQL.addMatch;
import static Database.PlayerRequestSQL.*;
import static StaticMethode.MethodeJtable.*;
import static StaticMethode.TableModel.playerTable;


public class AddMatch extends JFrame{
    private JTable tableVainqueur;
    private JTable tableFinaliste;
    private JButton definirLEpreuveButton;
    private JButton annulerButton;
    private JTextField rechercheVainqueur;
    private JTextField rechercheFinaliste;
    private JPanel panel;
    private JButton chercherButton;
    private JButton chercherButton1;

    private ResultSet rs;

    public AddMatch(TournoisMain parent, long idEpreuve, String sexe) {
        initPlayerTable(sexe);
        this.setTitle("Ajout d'un match pour l'epreuve "+ idEpreuve);
        this.setSize(580,580);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setContentPane(this.panel);
        initJtable(tableFinaliste);
        initJtable(tableVainqueur);
        tableFinaliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableVainqueur.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        definirLEpreuveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ajouterMatch(idEpreuve)) {
                    parent.refreshTournoiTab();
                    dispose();
                }
            }
        });
        chercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toSearch = rechercheVainqueur.getText();
                if (toSearch.length()>0){
                    remplirPlayerTable(tableVainqueur,searchPlayer(toSearch));
                }else{
                    remplirPlayerTable(tableVainqueur,rs);
                }
            }
        });


        chercherButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toSearch = rechercheFinaliste.getText();
                if (toSearch.length()>0){
                    remplirPlayerTable(tableFinaliste,searchPlayer(toSearch));
                }else{
                    remplirPlayerTable(tableFinaliste,rs);
                }
            }
        });


        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initPlayerTable(String sexe){
        ResultSet rs = null;
        if(sexe.equals("F")) {
            this.rs = getAllGirl();
        }else {
            this.rs = getAllMen();
        }
        remplirJTable(tableVainqueur, playerTable(), this.rs);
        try {
            this.rs.beforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        remplirJTable(tableFinaliste, playerTable(), this.rs);
    }
    private boolean ajouterMatch(long idEpreuve) {
        // Récupérer les identifiants des joueurs sélectionnés dans les tables
        long idVainqueur = (long) tableVainqueur.getValueAt(tableVainqueur.getSelectedRow(), 0);
        long idFinaliste = (long) tableFinaliste.getValueAt(tableFinaliste.getSelectedRow(), 0);

        // Appeler la méthode addMatch pour ajouter le nouveau match à l'épreuve
        return addMatch(idEpreuve, idVainqueur, idFinaliste);
    }

    private void remplirPlayerTable(JTable table,ResultSet rs){
        try {
            this.rs.beforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        remplirJTable(table, playerTable(), rs);
    }

}
