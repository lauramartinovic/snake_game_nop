package view;

import controller.GameController;
import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem highscoresMenuItem;
    private JMenuItem exitMenuItem;

    public GameFrame(String playerName) {
        Game game = new Game();
        GamePanel gamePanel = new GamePanel(game, playerName, this);
        GameController gameController = new GameController(game);

        this.add(gamePanel);
        this.addKeyListener(gameController);  // Povezivanje kontrolera s okvirom

        // Oslušaj fokus na prozoru
        this.setFocusable(true);  // Omogućujemo da JFrame primi fokus za tipkovnicu
        this.requestFocusInWindow();

        // Inicijaliziramo JMenuBar i opcije
        menuBar = new JMenuBar();
        menu = new JMenu("Options");

        // Stavke u meniju
        highscoresMenuItem = new JMenuItem("Highscores");
        exitMenuItem = new JMenuItem("Exit");

        // Dodaj stavke u meni
        menu.add(highscoresMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);

        // Postavi JMenuBar na prozor
        this.setJMenuBar(menuBar);

        // Akcija za prikaz highscores
        // Akcija za prikaz highscores rezultata
        highscoresMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Highscores();  // Otvori prozor za prikaz highscores rezultata
            }
        });


        // Akcija za izlazak iz igre
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Izlaz iz igre
            }
        });

        // Osnovne postavke prozora
        setVisible(true);
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // Metoda za prikaz highscores rezultata
    private void displayHighscores() {
        List<String> topScores = getTopScores();  // Dohvaćamo top 10 rezultata iz baze

        // Prikaz rezultata u dijalogu
        StringBuilder highscoresText = new StringBuilder("Top 10 Highscores:\n");
        for (String score : topScores) {
            highscoresText.append(score).append("\n");
        }

        JOptionPane.showMessageDialog(this, highscoresText.toString(), "Highscores", JOptionPane.INFORMATION_MESSAGE);
    }

    // Metoda koja dohvaća top 10 rezultata iz baze podataka
    private List<String> getTopScores() {
        List<String> scores = new ArrayList<>();
        String query = "SELECT player_name, score FROM highscores ORDER BY score DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snake_game", "root", "shipwreck0");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");
                scores.add(playerName + " - " + score);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }


}
