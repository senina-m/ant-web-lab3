package ru.nanikon.third.service.model;

import java.util.function.BiPredicate;

/**
 * @author Natalia Nikonova
 */
public enum Quarter {
   FIRST((x, y) -> (x >= 0) && (y >= 0)),
   SECOND((x, y) -> (x <= 0) && (y >= 0)),
   THIRD((x, y) -> (x <= 0) && (y <= 0)),
   FOURTH((x, y) -> (x >= 0) && (y <= 0));

   BiPredicate<Double, Double> predicate;

   Quarter(BiPredicate<Double, Double> predicate) {
      this.predicate = predicate;
   }

   public boolean check(Double x, Double y) {
      return predicate.test(x, y);
   }
}
