package ru.nanikon.third.service;

import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.service.model.Shape;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;

/** Processes the user's request: determines whether the shot hit the area
 *
 * @author Natalia Nikonova
 */
@ManagedBean(name = "areaCheckService")
@ApplicationScoped
public class AreaService {
    private final ArrayList<Shape> parts = new ArrayList<>();

    public void addShape(Shape shape) {
        parts.add(shape);
    }

    public void checkArea(ShotEntity shot) {
        boolean result = false;
        for (Shape shape : parts) {
            result = result || shape.checkHit(shot.getX(), shot.getY(), (double) shot.getR());
        }
        shot.setHit(result);
    }
}
