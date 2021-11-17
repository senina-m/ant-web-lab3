package ru.nanikon.third.service.model;

/**
 * @author Natalia Nikonova
 */
abstract public class Shape {
    private final Quarter quarter;

    public Shape(Quarter quarter) {
        this.quarter = quarter;
    }

    public boolean checkHit(Double x, Double y, Double r) {
        return quarter.check(x, y);
    }
}
