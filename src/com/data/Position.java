package com.data;

import com.services.PositionI;

public class Position implements PositionI {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double distance(PositionI other) {
        if(other instanceof Position){
            Position autre = (Position) other;
            return Math.abs(Math.sqrt((autre.getX()-this.getX())*(autre.getX()-this.getX())
                    -(autre.getY()-this.getY())*(autre.getY()-this.getY())));
        }
        return Double.MAX_VALUE;
    }
}
