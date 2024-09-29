package controller;

import model.Game;
import model.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && game.getSnake().getDirection() != Direction.DOWN) {
            game.getSnake().setDirection(Direction.UP);
        } else if (key == KeyEvent.VK_DOWN && game.getSnake().getDirection() != Direction.UP) {
            game.getSnake().setDirection(Direction.DOWN);
        } else if (key == KeyEvent.VK_LEFT && game.getSnake().getDirection() != Direction.RIGHT) {
            game.getSnake().setDirection(Direction.LEFT);
        } else if (key == KeyEvent.VK_RIGHT && game.getSnake().getDirection() != Direction.LEFT) {
            game.getSnake().setDirection(Direction.RIGHT);
        }
    }
}
