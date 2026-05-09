package com.bjtu.canteensimulation.model.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Seat {
    private int id;
    private boolean occupied;

    public Seat(int id) {
        this.id = id;
        this.occupied = false;
    }

    public int getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void draw(GraphicsContext gc, double x, double y, double width, double height) {
        if (occupied) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(Color.LIGHTBLUE);
        }
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }
}