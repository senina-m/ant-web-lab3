package ru.nanikon.third.service.model;

/** Rhomb centered at the origin
 *
 * @author Natalia Nikonova
 */
public class Rhomb extends Shape{
   public Rhomb(Quarter quarter) {
      super(quarter);
   }

   @Override
   public boolean checkHit(Double x, Double y, Double r) {
      return super.checkHit(x, y, r)
              && (y <= -x + r)
              && (y >= x - r)
              && (y >= -x -r)
              && (y <= x + r);
   }
}
