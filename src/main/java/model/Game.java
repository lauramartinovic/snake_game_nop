package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Snake snake;
    private Food food;
    private boolean running;
    private int score;
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

    public int getScore() {
        return score;
    }

    public boolean isRunning() {
        return running;
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
        snake.move();  // Pomicanje zmije

        // Provjera da li je zmija pojela hranu
        if (snake.getBody().get(0).equals(food.getPosition())) {
            snake.grow();  // Zmija raste kada pojede hranu
            food.generateNewPosition();  // Generiraj novu poziciju za hranu
            score++;  // Povećaj rezultat
        }

        // Dinamičke granice panela
        int panelWidthInUnits = 800 / 25;  // 800 je širina panela, 25 je veličina jedinice
        int panelHeightInUnits = 480 / 25; // 480 je visina panela, 25 je veličina jedinice

        // Provjera sudara sa zidom ili tijelom zmije
        Point head = snake.getBody().get(0);
        boolean hitWall = head.x < 0 || head.x >= panelWidthInUnits || head.y < 0 || head.y >= panelHeightInUnits;
        boolean hitItself = snake.checkCollision();

        if (hitWall || hitItself) {
            running = false;  // Ako zmija udari u zid ili samu sebe, igra završava
        }

        // Ako nema sudara, igra se nastavlja
        if (running) {
            notifyObservers();  // Obavijesti promatrače o promjeni stanja igre
        }
    }


}




