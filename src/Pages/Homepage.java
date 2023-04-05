package Pages;

import Database.ConnectionSQL;
import Pages.Player.PlayerPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame{
    private final Homepage thisHomePage = this;
    private JButton connectTo;
    private JLabel CoStatus;
    private JPanel mainPanel;
    private PlayerPage page1;

    public Homepage() {
        connectTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startIfConnection();
            }
        });

    }
    public Homepage start(){
        this.setContentPane(this.mainPanel);
        this.setTitle("test");
        this.setSize(1080,720);
        this.setMaximumSize(new Dimension(720,540));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startIfConnection();
        return this;
    }

    private void startIfConnection(){
        if(ConnectionSQL.initCo()){
            CoStatus.setText("Connexion réussie.");
            mainPanel.setBackground(Color.green);
            TabbedPage p = new TabbedPage(thisHomePage);
        }else{
            CoStatus.setText("Echec de la connexion");
            mainPanel.setBackground(Color.red);
        };
    }
}
