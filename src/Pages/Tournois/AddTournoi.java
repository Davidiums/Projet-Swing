package Pages.Tournois;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Database.TournoisRequestSQL.addTournoi;
import static StaticMethode.MethodeTextField.setMaxChar;

public class AddTournoi extends JFrame {

    private JButton ajouterButton;
    private JButton annulerButton;
    private JTextField nameField;
    private JLabel codeLabel;
    private JTextField codeField;
    private JLabel nomLabel;
    private JPanel panel;

    public AddTournoi(TournoisMain parent) {
        this.setTitle("Ajout d'un tournoi");
        this.setSize(540,160);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setContentPane(this.panel);
        setMaxChar(codeField, 2);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addTournoi(nameField.getText(),codeField.getText()))parent.refreshTournoiTab();
                dispose();
            }
        });
    }
}
