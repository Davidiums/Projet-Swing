package Pages;

import Pages.Player.PlayerPage;
import Pages.Tournois.TournoisMain;

import javax.swing.*;

public class TabbedPage extends JFrame{

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;

    public TabbedPage(JFrame parentFrame) {
        parentFrame.setContentPane(mainPanel);
        parentFrame.setTitle("Tennis Mannager");
        parentFrame.setSize(1080, 720);
        parentFrame.setVisible(true);
        parentFrame.setContentPane(mainPanel);
        PlayerPage pPage = new PlayerPage();
        tabbedPane.addTab("Joueurs", pPage.getPlayerPanel());
        TournoisMain tPage = new TournoisMain();
        tabbedPane.addTab("Tournois", tPage.getMainPanel());

    }
}
