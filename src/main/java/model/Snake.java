package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private List<Point> body;
    private Direction direction;

    public Snake() {
        super(new Point(5, 5));
        body = new ArrayList<>();
        body.add(new Point(5, 5)); // početna pozicija
        direction = Direction.RIGHT;
    }

    public List<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        move();
    }

    public void move() {
        Point head = body.get(0);  // Glava zmije
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }

        body.add(0, newHead);  // Dodaj novu glavu na početak tijela
        body.remove(body.size() - 1);  // Ukloni zadnji segment (pomicanje)
    }

    public void grow() {
        body.add(new Point(body.get(body.size() - 1))); // dodaj segment na kraj tijela
    }

    public boolean checkCollision() {
        Point head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;  // Sudar sa samim sobom
            }
        }
        return false;
    }
}
