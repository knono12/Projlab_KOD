package view.popups;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.MainFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class GameOverframe extends JDialog {
    
    public final JLabel resultLabel;
    public final JButton backToMenuButton;

    MainFrame mainFrame;

    public GameOverframe(JFrame parent, MainFrame mainFrame) {
        super(parent, "Game Over", true);

        this.mainFrame = mainFrame;

        resultLabel = new JLabel("", SwingConstants.CENTER); // TODO: Set text when showing the results of the game
        backToMenuButton = new JButton("Back to Menu");
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout(8, 8));

        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setPreferredSize(new Dimension(320, 80));
        add(resultLabel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(backToMenuButton);
        add(bottom, BorderLayout.SOUTH);

        backToMenuButton.addActionListener(e -> {
            dispose();
            mainFrame.showPanel(MainFrame.PanelName.MAIN_MENU_PANEL);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    public void setResult(String text) {
        resultLabel.setText(text);
    }

    public void showGameOverDialog() {
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}
