package ru.nanikon.third.bean;

import ru.nanikon.third.entity.ShotEntity;
import lombok.Getter;
import lombok.Setter;
import ru.nanikon.third.service.AreaService;
import ru.nanikon.third.service.model.HorizontalRect;
import ru.nanikon.third.service.model.LittleCircle;
import ru.nanikon.third.service.model.Quarter;
import ru.nanikon.third.service.model.Rhomb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shotList")
@SessionScoped
public class ShotListBean {
   @Getter
   @Setter
   private List<ShotEntity> shotList = new LinkedList<>();

   @Getter
   @Setter
   @ManagedProperty(value = "#{areaCheck}")
   private AreaService areaService;

   @PostConstruct
   private void init() {
      areaService.addShape(new Rhomb(Quarter.FIRST));
      areaService.addShape(new HorizontalRect(Quarter.SECOND));
      areaService.addShape(new LittleCircle(Quarter.THIRD));
   }

   public void addShot(ShotBean shot) {
      ShotEntity newShot = new ShotEntity(shot.getX(), shot.getY(), shot.getR());
      areaService.checkArea(newShot);
      shotList.add(newShot);
   }
}
