package view.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.MainFrame;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Component;

public class OneTeamNamePanel extends JPanel {
    private final JLabel promptLabel = new JLabel("Enter your name:");
    private final JTextField nameField = new JTextField(20);
    private final JButton okButton = new JButton("OK");
    private final JButton backButton = new JButton("Back");

    private MainFrame mainFrame;

    public OneTeamNamePanel(MainFrame mf) {

        this.mainFrame = mf;
        initialize();
        addButtonListeners();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setMaximumSize(new Dimension(240, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(promptLabel);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(nameField);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(okButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
    }

    private void okButtonClicked() {
        // TODO: Implement the name saving logic here, and then switch to the next panel
        
        // For testing purposes, print the entered name to the console
        System.out.println("Name: " + getEnteredName()); 
    }
    
    private void addButtonListeners() {
        nameField.addActionListener(event -> okButtonClicked());
        okButton.addActionListener(event -> okButtonClicked());
        backButton.addActionListener(event -> mainFrame.showPanel(view.MainFrame.PanelName.MAIN_MENU_PANEL));
    }

    public void setPrompt(String text) {
        promptLabel.setText(text);
    }

    public String getEnteredName() {
        return nameField.getText().trim();
    }

    public JLabel getPromptLabel() {
        return promptLabel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

}
