package model;

import java.awt.*;

public abstract class GameObject {
    protected Point position;

    public GameObject(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract void update();
}
