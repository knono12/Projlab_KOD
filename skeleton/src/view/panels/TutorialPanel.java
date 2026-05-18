package view.panels;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import java.awt.Dimension;
import java.awt.Component;

import view.MainFrame;

public class TutorialPanel extends JPanel {
    public final JTextPane tutorialTextPane;
    public final JButton backButton;

    private final MainFrame mainFrame;

    public TutorialPanel(MainFrame mf) {
        this.mainFrame = mf;
        tutorialTextPane = new JTextPane();
        backButton = new JButton("Back");

        initialize();
        addButtonListeners();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        tutorialTextPane.setEditable(false);
        tutorialTextPane.setContentType("text/html");
        tutorialTextPane.setEditorKit(new HTMLEditorKit());

        StyleSheet styleSheet = ((HTMLEditorKit) tutorialTextPane.getEditorKit()).getStyleSheet();
        styleSheet.addRule("body { font-family: Sans-Serif; font-size: 12pt; text-align: justify; margin: 8px; }");

        tutorialTextPane.setText("<html><body></body></html>");

        JScrollPane scrollPane = new JScrollPane(tutorialTextPane);
        scrollPane.setPreferredSize(new Dimension(680, 420));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(scrollPane);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(backButton);
        add(Box.createRigidArea(new Dimension(0, 24)));
    }

    private void addButtonListeners() {
        backButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.MAIN_MENU_PANEL));
    }
}
