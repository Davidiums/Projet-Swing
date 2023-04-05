package Pages.Tournois;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static StaticMethode.MethodeTextField.setMaxChar;
import static Database.TournoisRequestSQL.*;

public class ModifyTournoi extends JFrame {
    private JPanel panel;
    private JButton modifyButton;
    private JButton annulerButton;
    private JLabel codeLabel;
    private JTextField nameField;
    private JTextField codeField;
    private JLabel nomLabel;

    public ModifyTournoi(TournoisMain parent, long id, String name){
        this.setTitle("Modification d'un tournoi : "+ name);
        this.setSize(540,160);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setContentPane(this.panel);
        setMaxChar(codeField, 2);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText();
                String newCode = codeField.getText();

                // Vérifier si les champs name et code sont vides
                if (newName.isEmpty() && newCode.isEmpty()) {
                    dispose();
                    return;
                }

                if (modifyTournoi(newName, newCode, id)) {
                    parent.refreshTournoiTab();
                    parent.refreshYearSelection();
                    dispose();
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

}
