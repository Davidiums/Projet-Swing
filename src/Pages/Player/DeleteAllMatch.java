package Pages.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static Database.MatchRequestSQL.deletePlayersMatch;
import static Database.MatchRequestSQL.showPlayersMatchs;
import static StaticMethode.TableModel.allMatchbyPlayerTable;
import static StaticMethode.MethodeJtable.remplirJTable;

public class DeleteAllMatch extends JFrame{
    private JPanel panel;
    private JTable table1;
    private JButton confirmerButton;
    private JButton annulerButton;

    public DeleteAllMatch(PlayerPage parent, List<Long> idPlayer) {
        this.setTitle("Recupitulatif de suppression");
        this.setSize(540,160);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setContentPane(this.panel);
        remplirJTable(table1,allMatchbyPlayerTable(),showPlayersMatchs(idPlayer));
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deletePlayersMatch(idPlayer));
                parent.refreshTable();
                dispose();
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


