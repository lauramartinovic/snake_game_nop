package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Snake snake;
    private Food food;
    private boolean running;
    private int score;
    private String playerName;
    private List<GameObserver> observers = new ArrayList<>();

    public Game() {
        snake = new Snake();
        food = new Food();
        running = true;
        score = 0;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean isRunning() {
        return running;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    public void update() {
        snake.move();
        // Provjera sudara zmije sa zidovima
        if (checkWallCollision() || snake.checkCollision()) {
            running = false;  // Završava igru ako dođe do sudara
        }

        notifyObservers();
    }

    private boolean checkWallCollision() {
        // Provjeri da li zmija udara u zidove panela
        Point head = snake.getBody().get(0);
        return head.x < 0 || head.x >= 32 || head.y < 0 || head.y >= 20;
    }

    public void increaseScore(int increment) {
        score += increment;
    }
}
