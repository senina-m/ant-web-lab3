package ru.nanikon.third.service.model;

/** Rect centered at the origin, height r / 2 and width r
 *
 * @author Natalia Nikonova
 */
public class HorizontalRect extends Shape{
   public HorizontalRect(Quarter quarter) {
      super(quarter);
   }

   @Override
   public boolean checkHit(Double x, Double y, Double r) {
      return super.checkHit(x, y, r) && (x >= -r) && (x <= r) && (y >= -r / 2) && (y <= r / 2);
   }
}
