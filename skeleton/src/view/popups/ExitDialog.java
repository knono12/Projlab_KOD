package view.popups;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ExitDialog {

    private ExitDialog() {
        // Private constructor to prevent instantiation
    }

    public static boolean showConfirm(JFrame parent) {
        int result = JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return result == JOptionPane.YES_OPTION;
    }

}
