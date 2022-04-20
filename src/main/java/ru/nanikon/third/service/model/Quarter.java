package ru.nanikon.third.service.model;

import java.util.function.BiPredicate;

/** Enum of quaters. Cuts off from the figure those areas of it that do not fall into the specified quarter
 *
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
