package ru.nanikon.third.service.model;

/**
 * @author Natalia Nikonova
 */
public class Circle extends Shape{
    public Circle(Quarter quarter) {
        super(quarter);
    }

    @Override
    public boolean checkHit(Double x, Double y, Double r) {
        return super.checkHit(x, y, r) && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2));
    }
}
