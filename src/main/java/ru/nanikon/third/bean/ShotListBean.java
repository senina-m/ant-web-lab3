package ru.nanikon.third.bean;

import ru.nanikon.third.model.ShotEntity;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
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

   public void addShot(ShotBean shot) {
      shotList.add(new ShotEntity(shot.getX(), shot.getY(), shot.getR(), true));
   }
}
