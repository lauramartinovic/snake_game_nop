package app;

import view.Mainframe;

public class App {
    public static void main(String[] args) {
        // Pokreni početnu stranicu (HomePage)
        javax.swing.SwingUtilities.invokeLater(() -> {
            Mainframe mainframe = new Mainframe();
            mainframe.setVisible(true);
        });
    }
}
