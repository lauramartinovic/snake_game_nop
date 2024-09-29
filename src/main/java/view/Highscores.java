package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class Highscores extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public Highscores() {
        setTitle("Highscores");
        setSize(800, 480);
        setLocationRelativeTo(null);  // Centriranje prozora
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Stvaramo tabelu za prikaz rezultata
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Player");
        tableModel.addColumn("Score");
        table = new JTable(tableModel);

        // Dohvaćamo rezultate i popunjavamo tablicu
        loadHighscores();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    // Metoda koja dohvaća top 10 rezultata iz baze podataka
    private void loadHighscores() {
        String query = "SELECT player_name, score FROM highscores ORDER BY score DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snake_game", "root", "shipwreck0");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");

                Vector<Object> row = new Vector<>();
                row.add(playerName);
                row.add(score);

                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
