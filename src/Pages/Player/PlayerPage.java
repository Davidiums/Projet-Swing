package Pages.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import static StaticMethode.TableModel.playerTable;
import static StaticMethode.MethodeJtable.*;
import static Database.PlayerRequestSQL.*;


public class PlayerPage extends JFrame {

    private JRadioButton femmesRadioButton;
    private JRadioButton hommesRadioButton;
    private JRadioButton tousRadioButton;
    private JPanel mainPanel;
    private JTable table1;
    private JTextField lastName;
    private JRadioButton H;
    private JRadioButton F;
    private JButton addPlayerButton;
    private JPanel playerPanel;
    protected JTextField searchArea;
    protected JButton searchButton;
    private JTextField firstName;
    private JPanel newPlayer;
    private JLabel nom;
    private JButton deleteButton;
    private JButton modifier;
    private JPanel modifyPanel;
    private JRadioButton modifyToH;
    private JRadioButton modifyToF;
    private JTextField modifyLastName;
    private JTextField modifyFirstName;
    private JLabel modifyDone;
    private JScrollBar scrollBar;

    public JPanel getPlayerPanel() {
        return playerPanel;
    }

    public PlayerPage() {
        initJtable(table1);
        remplirPlayerTable(getAllPlayer());
        stopSelection(table1, playerPanel);
        deleteButton.setVisible(false);
        modifyPanel.setVisible(false);
        modifyDone.setVisible(false);
        femmesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplirPlayerTable(getAllGirl());
            }
        });



        hommesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplirPlayerTable(getAllMen());
            }
        });


        tousRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplirPlayerTable(getAllPlayer());
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table1.getSelectedRows();

                int confirm = JOptionPane.showConfirmDialog(PlayerPage.this, "Êtes-vous sûr de vouloir supprimer la sélection ?");

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Long> ids = new ArrayList<>();

                    for (int selectedRow : selectedRows) {
                        Long currentID = (Long) table1.getValueAt(selectedRow, 0);
                        ids.add(currentID);
                    }
                    boolean success = deletePlayers(ids);
                    if (success) {
                        remplirPlayerTable(getAllPlayer());
                    } else {
                        int confirm2 = JOptionPane.showConfirmDialog(PlayerPage.this, "Voulez-vous supprimer les joueurs assignés à un match ?");
                        if (confirm2 == JOptionPane.YES_OPTION) {
                            DeleteAllMatch page = new DeleteAllMatch(PlayerPage.this ,ids);
                        }
                    }
                }
            }
        });






        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentSexe;
                String firstname = firstName.getText();
                String lastname = lastName.getText();
                if (H.isSelected()) {
                    currentSexe = "H";
                } else if (F.isSelected()) {
                    currentSexe = "F";
                } else {
                    // aucun des deux boutons n'est sélectionné, on assigne une valeur par défaut
                    currentSexe = "";
                }
                addPlayer(firstname, lastname, currentSexe);
                remplirPlayerTable(searchPlayer(firstname+" "+lastname));
            }
        });




        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplirPlayerTable(searchPlayer(searchArea.getText()));
            }
        });




        //Activer ou desactiver les bouton de suppression et modification
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (table1.getSelectedRowCount() > 0) {
                deleteButton.setVisible(true);
                deleteButton.setEnabled(true); // activer le bouton si une ligne est sélectionnée
            } else {
                deleteButton.setVisible(false);
                deleteButton.setEnabled(false); // désactiver le bouton sinon
            }
            if (table1.getSelectedRowCount() == 1) {
                modifyPanel.setEnabled(true);
                modifyPanel.setVisible(true);
            } else {
                modifyPanel.setEnabled(false);
                modifyPanel.setVisible(false);
            }
        });
        //########################################################################




        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long currentID = (Long) table1.getModel().getValueAt(table1.getSelectedRow(), 0);
                String firstName = modifyFirstName.getText();
                String lastName = modifyLastName.getText();
                String newSexe = "noModify";
                modifyDone.setVisible(true);
                if (modifyToH.isSelected()) newSexe = "H";
                else if (modifyToF.isSelected()) newSexe = "F";
                if (updatePlayer(currentID, firstName, lastName, newSexe)) {
                    modifyDone.setText("Modification effectuée!");
                    remplirPlayerTable(searchPlayer(currentID.toString()));
                    table1.setRowSelectionInterval(0, 1);
                }else{
                    modifyDone.setText("Echec de la modification.");
                }

            }
        });




        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void refreshTable(){
        remplirPlayerTable(getAllPlayer());
    }
    private void remplirPlayerTable(ResultSet rs){
        remplirJTable(table1, playerTable(), rs);
    }



}
