package view;

import model.Game;
import model.GameObserver;
import model.Snake;
import model.Food;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GamePanel extends JPanel implements GameObserver {
    private Game game;
    private Timer timer;
    private String playerName;  // Ime igrača

    // Ažurirani konstruktor koji prima ime igrača
    public GamePanel(Game game, String playerName, JFrame gameFrame) {
        this.game = game;
        this.playerName = playerName;  // Postavi ime igrača
        this.setPreferredSize(new Dimension(800, 480));
        this.setBackground(Color.BLACK);

        // Registriramo se kao promatrač igre
        game.addObserver(this);

        // Inicijaliziramo i pokrećemo timer
        timer = new Timer(100, e -> {
            if (game.isRunning()) {
                game.update();  // Ažuriramo stanje igre (pomičemo zmiju, provjeravamo hranu, sudare)
                repaint();  // Crtamo ažurirani prikaz igre
            } else {
                gameOver(gameFrame);  // Ako igra završi, prikazujemo kraj
            }
        });
        timer.start();  // Pokrećemo tajmer za redovito ažuriranje igre
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        Snake snake = game.getSnake();
        Food food = game.getFood();

        // Crtanje hrane
        g.setColor(Color.RED);
        g.fillOval(food.getPosition().x * 25, food.getPosition().y * 25, 25, 25);

        // Crtanje zmije
        g.setColor(Color.GREEN);
        for (Point p : snake.getBody()) {
            g.fillRect(p.x * 25, p.y * 25, 25, 25);
        }

        // Crtanje rezultata
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore(), 10, 10);
    }

    @Override
    public void update() {
        // Ažuriramo prikaz kada dođe do promjene stanja u igri
        repaint();
    }

    // Kraj igre
    private void gameOver(JFrame gameFrame) {
        timer.stop();  // Zaustavljamo timer
        saveScoreToDatabase(game.getScore());  // Spremamo rezultat u bazu podataka

        // Prikazujemo highscore prozor
        Highscores highscoresWindow = new Highscores();
        highscoresWindow.setVisible(true);  // Otvaramo novi prozor za Highscores

        // Zatvaramo trenutni prozor igre
        gameFrame.dispose();  // Zatvara prozor igre

        // Prikazujemo poruku za kraj igre (možeš ovo ukloniti ili zadržati)
        JOptionPane.showMessageDialog(null, "Game Over! Your score: " + game.getScore(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }


    // Spremanje rezultata u bazu podataka
    private void saveScoreToDatabase(int score) {
        String query = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snake_game", "root", "shipwreck0");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, playerName);  // Postavi ime igrača
            pstmt.setInt(2, score);  // Postavi rezultat igrača
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
