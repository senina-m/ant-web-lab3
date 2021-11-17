package ru.nanikon.third.service.model;

/**
 * @author Natalia Nikonova
 */
public class LittleCircle extends Circle {
    public LittleCircle(Quarter quarter) {
        super(quarter);
    }

    @Override
    public boolean checkHit(Double x, Double y, Double r) {
        return super.checkHit(x, y, r / 2);
    }
}
