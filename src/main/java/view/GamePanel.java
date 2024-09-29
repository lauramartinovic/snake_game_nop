package view;

import model.Game;
import model.GameObserver;
import model.Snake;
import model.Food;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements GameObserver {
    private Game game;
    private Timer timer;
    private int appleX, appleY;
    private int currentDelay = 100;
    private Random random = new Random();
    private final int UNIT_SIZE = 25;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 480;
    private final int initialPos = 50;
    private boolean running = true;

    public GamePanel(Game game) {
        this.game = game;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        // Registriramo se kao promatrač igre
        game.addObserver(this);

        // Inicijaliziramo i pokrećemo timer
        timer = new Timer(currentDelay, e -> {
            if (game.isRunning()) {
                game.update();  // Ažuriramo stanje igre (pomičemo zmiju, provjeravamo hranu, sudare)
                repaint();  // Crtamo ažurirani prikaz igre
            } else {
                gameOver();
            }
        });
        timer.start();  // Pokrećemo tajmer za redovito ažuriranje igre
        newApple();
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
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        // Crtanje zmije
        g.setColor(Color.GREEN);
        for (Point p : snake.getBody()) {
            g.fillRect(p.x * UNIT_SIZE, p.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        // Crtanje rezultata
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore(), 10, 10);
    }

    private void newApple() {
        boolean onSnake = true;
        while (onSnake) {
            appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = (random.nextInt((int) ((SCREEN_HEIGHT - initialPos - UNIT_SIZE) / UNIT_SIZE)) * UNIT_SIZE) + initialPos;

            // Provjeri je li jabuka generirana na tijelu zmije
            onSnake = false;
            for (Point p : game.getSnake().getBody()) {
                if (p.x * UNIT_SIZE == appleX && p.y * UNIT_SIZE == appleY) {
                    onSnake = true;
                    break;
                }
            }
        }
    }

    private void gameOver() {
        timer.stop();  // Zaustavljamo timer
        JOptionPane.showMessageDialog(this, "Game Over!\nScore: " + game.getScore(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        // Pozovi Highscore prozor
        Highscores highscores = new Highscores();
        highscores.setVisible(true);
        SwingUtilities.getWindowAncestor(this).dispose(); // Zatvori trenutni prozor igre
    }

    @Override
    public void update() {
        // Provjera da li je zmija pojela jabuku
        if (game.getSnake().getBody().get(0).x * UNIT_SIZE == appleX && game.getSnake().getBody().get(0).y * UNIT_SIZE == appleY) {
            game.getSnake().grow();
            game.increaseScore(5);
            newApple();  // Generiraj novu jabuku
        }
        repaint();
    }
}

