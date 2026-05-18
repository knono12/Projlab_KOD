package view.panels;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import application.LeaderBoardEntry;
import view.MainFrame;

import java.awt.BorderLayout;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private JTable entriesTable;
    private JButton backButton;
    
    private DefaultTableModel tableModel;

    private MainFrame mainFrame;

    public LeaderboardPanel(MainFrame mf) {
        mainFrame = mf;
        
        initialize();
        addButtonListeners();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[] { "Name", "Score" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        entriesTable = new JTable(tableModel);
        entriesTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(entriesTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        add(backButton, BorderLayout.SOUTH);
    }
    
    private void addButtonListeners() {
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.PanelName.MAIN_MENU_PANEL));
    }
    
    public void setEntries(List<LeaderBoardEntry> entries) {
        tableModel.setRowCount(0);
        if (entries == null) {
            return;
        }
        
        for (LeaderBoardEntry entry : entries) {
            // TODO: Format the scores
        }
    }
}
