package com.data;

import com.services.PositionI;

/**
 * The type Position.
 */
public class Position implements PositionI {

    private double x;
    private double y;

    /**
     * Descripton: Instantiates a new Position.
     * @param x of type double - the x
     * @param y of type double - the y
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Description: Gets x.
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Description: Gets y.
     * @return the y
     */
    public double getY() {
        return y;
    }

    @Override
    public double distance(PositionI other) {
        if(other instanceof Position){
            Position autre = (Position) other;
            return Math.abs(Math.sqrt((autre.getX()-this.getX())*(autre.getX()-this.getX())
                    +(autre.getY()-this.getY())*(autre.getY()-this.getY())));
        }
        return Double.MAX_VALUE;
    }
}