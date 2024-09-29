package model;

import java.awt.*;
import java.util.Random;

public class Food extends GameObject {
    private static final int GRID_SIZE = 20;
    private Random random;

    public Food() {
        super(new Point(0, 0));
        random = new Random();
        generateNewPosition();
    }

    @Override
    public void update() {
        // Hrana se ne kreće, nema dodatne logike u update metodi
    }

    public void generateNewPosition() {
        this.position = new Point(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE));
    }
}
