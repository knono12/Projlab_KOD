package view.panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;

import view.MainFrame;
import view.popups.ExitDialog;

public class MainMenuPanel extends JPanel {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 30;

    private JLabel titleLine1 = new JLabel("Snowplow", SwingConstants.CENTER);
    private JLabel titleLine2 = new JLabel("Game", SwingConstants.CENTER);
    private JButton onePlayerButton = new JButton("One player");
    private JButton twoPlayerButton = new JButton("Two player");
    private JButton leaderBoardButton = new JButton("Leaderboard");
    private JButton tutorialButton = new JButton("Tutorial");
    private JButton exitButton = new JButton("Exit");

    private MainFrame mainFrame;

    public MainMenuPanel(MainFrame mf) {

        this.mainFrame = mf;
        initialize();
        addButtonListeners();
    }

    void initialize() {
        setLayout(new GridBagLayout());

        titleLine1.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLine2.setFont(new Font("SansSerif", Font.BOLD, 32));

        Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        onePlayerButton.setPreferredSize(buttonSize);
        twoPlayerButton.setPreferredSize(buttonSize);
        leaderBoardButton.setPreferredSize(buttonSize);
        tutorialButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(100, 0, 4, 0);
        add(titleLine1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 16, 0);
        add(titleLine2, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(new JPanel(), gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(8, 24, 8, 24);

        gbc.gridy = 3;
        add(onePlayerButton, gbc);

        gbc.gridy = 4;
        add(twoPlayerButton, gbc);

        gbc.gridy = 5;
        add(leaderBoardButton, gbc);

        gbc.gridy = 6;
        add(tutorialButton, gbc);

        gbc.gridy = 7;
        add(exitButton, gbc);

        gbc.gridy = 8;
        gbc.weighty = 0.01;
        add(new JPanel(), gbc);
    }

    void addButtonListeners() {
        onePlayerButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.ONE_TEAM_NAME_PANEL));
        twoPlayerButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.TWO_TEAM_NAME_PANEL));
        leaderBoardButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.LEADERBOARD_PANEL));
        tutorialButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.TUTORIAL_PANEL));
        exitButton.addActionListener(event -> {
            if (ExitDialog.showConfirm(mainFrame)) {
                System.exit(0);
            }
        });
    }
}
