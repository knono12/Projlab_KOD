package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;

import java.awt.CardLayout;

import view.panels.MainMenuPanel;
import view.panels.OneTeamNamePanel;
import view.panels.TwoTeamNamePanel;
import view.panels.LeaderboardPanel;
import view.panels.TutorialPanel;
import view.panels.GamePanel;

public class MainFrame extends JFrame {

    public enum PanelName {
        MAIN_MENU_PANEL,
        ONE_TEAM_NAME_PANEL,
        TWO_TEAM_NAME_PANEL,
        LEADERBOARD_PANEL,
        TUTORIAL_PANEL,
        GAME_PANEL
    }

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardContainer = new JPanel(cardLayout);

    public MainFrame() {
        setTitle("Snowplow game");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        cardContainer.add(PanelName.MAIN_MENU_PANEL.name(), new MainMenuPanel(this));
        cardContainer.add(PanelName.ONE_TEAM_NAME_PANEL.name(), new OneTeamNamePanel(this));
        cardContainer.add(PanelName.TWO_TEAM_NAME_PANEL.name(), new TwoTeamNamePanel(this));
        cardContainer.add(PanelName.LEADERBOARD_PANEL.name(), new LeaderboardPanel(this));
        cardContainer.add(PanelName.TUTORIAL_PANEL.name(), new TutorialPanel(this));
        cardContainer.add(PanelName.GAME_PANEL.name(), new GamePanel(this));

        this.add(cardContainer);
    }
    
    public void showPanel(PanelName panelName) {
        cardLayout.show(cardContainer, panelName.toString());
    }

}
