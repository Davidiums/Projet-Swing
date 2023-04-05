package Pages.Tournois;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Database.EpreuveRequestSQL.addEpreuveMixte;
import static Database.EpreuveRequestSQL.addEpreuveNotMixte;
import static StaticMethode.MethodeTextField.acceptOnlyNumbers;
import static StaticMethode.MethodeTextField.setMaxChar;

public class AddEpreuve extends JFrame{
    private String selectedSexe = "both";
    private JButton ajouterButton;
    private JButton annulerButton;
    private JTextField yearArea;
    private JRadioButton hommeRadioButton;
    private JRadioButton femmeRadioButton;
    private JRadioButton hommeEtFemmeRadioButton;
    private JPanel panel;

    public AddEpreuve(TournoisMain parent,long idTournoi, String tournoi) {
        this.setTitle("Ajout d'une épreuve au tournoi "+tournoi);
        this.setSize(540,160);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setContentPane(this.panel);
        acceptOnlyNumbers(yearArea);
        setMaxChar(yearArea, 4);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.parseInt(yearArea.getText());
                boolean success = false;
                if (selectedSexe.equals("both")){
                    if (addEpreuveMixte(year,idTournoi)) success = true;
                }else{
                    if (addEpreuveNotMixte(year, selectedSexe, idTournoi)) success=true;
                }
                if(success) {
                    parent.refreshMatchTab(year);
                    dispose();
                }else {
                    parent.setErrorArea("L'épreuve n'a pas pu être ajoutée.");
                }
            }
        });


        hommeEtFemmeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSexe = "both";
            }
        });


        hommeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSexe="H";
            }
        });


        femmeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSexe = "F";
            }
        });
    }
}
