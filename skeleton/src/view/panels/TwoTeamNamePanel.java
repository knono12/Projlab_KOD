package view.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.GridLayout;

import view.MainFrame;

public class TwoTeamNamePanel extends JPanel {
    private final JLabel promptLabel1 = new JLabel("Enter first name:");
    private final JLabel promptLabel2 = new JLabel("Enter second name:");
    private final JTextField nameField1 = new JTextField(15);
    private final JTextField nameField2 = new JTextField(15);
    private final JButton okButton = new JButton("OK");
    private final JButton backButton = new JButton("Back");

    private MainFrame mainFrame;

    public TwoTeamNamePanel(MainFrame mf) {
        this.mainFrame = mf;
        initialize();
        addButtonListeners();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        promptLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField1.setMaximumSize(new Dimension(220, 30));
        nameField2.setMaximumSize(new Dimension(220, 30));
        nameField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField2.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputRow = new JPanel(new GridLayout(1, 2, 0, 0));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(promptLabel1);
        leftPanel.add(nameField1);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(promptLabel2);
        rightPanel.add(nameField2);

        inputRow.add(leftPanel);
        inputRow.add(rightPanel);
        inputRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(Box.createVerticalGlue());
        add(inputRow);
        add(okButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createRigidArea(new Dimension(0, 24)));
    }

    private void addButtonListeners() {
        backButton.addActionListener(event -> mainFrame.showPanel(MainFrame.PanelName.MAIN_MENU_PANEL));
        nameField1.addActionListener(event -> okButtonClicked());
        nameField2.addActionListener(event -> okButtonClicked());
        okButton.addActionListener(event -> okButtonClicked());
    }
    
    private void okButtonClicked() {
        //TODO: Implement the name saving logic here, and then switch to the next panel
        
        // For testing purposes, print the entered names to the console
        System.out.println("First name: " + getEnteredName1()); 
        System.out.println("Second name: " + getEnteredName2());
    }
    
    public String getEnteredName1() {
        return nameField1.getText().trim();
    }

    public String getEnteredName2() {
        return nameField2.getText().trim();
    }

    public JLabel getPromptLabel1() {
        return promptLabel1;
    }

    public JLabel getPromptLabel2() {
        return promptLabel2;
    }

    public JTextField getNameField1() {
        return nameField1;
    }

    public JTextField getNameField2() {
        return nameField2;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
