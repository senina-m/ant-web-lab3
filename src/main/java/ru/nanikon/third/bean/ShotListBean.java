package ru.nanikon.third.bean;

import ru.nanikon.third.entity.ShotEntity;
import lombok.Getter;
import lombok.Setter;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.parser.ShotListJsonParser;
import ru.nanikon.third.service.AreaService;
import ru.nanikon.third.service.ShotService;
import ru.nanikon.third.service.UserService;
import ru.nanikon.third.service.model.HorizontalRect;
import ru.nanikon.third.service.model.LittleCircle;
import ru.nanikon.third.service.model.Quarter;
import ru.nanikon.third.service.model.Rhomb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shotList")
@SessionScoped
@Getter
@Setter
public class ShotListBean {
   private List<ShotEntity> shotList = new LinkedList<>();
   private String sessionId;
   private String jsonList;

   @ManagedProperty(value = "#{areaCheckService}")
   private AreaService areaService;
   @ManagedProperty(value = "#{userService}")
   private UserService userService;
   @ManagedProperty(value = "#{shotService}")
   private ShotService shotService;
   @ManagedProperty(value = "#{parser}")
   private ShotListJsonParser shotListJsonParser;

   @PostConstruct
   private void init() {
      areaService.addShape(new Rhomb(Quarter.FIRST));
      areaService.addShape(new HorizontalRect(Quarter.SECOND));
      areaService.addShape(new LittleCircle(Quarter.THIRD));
      sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
      System.out.println(sessionId);
      if (userService.isUserExists(sessionId)) {
         shotList = shotService.getAllBySessionId(sessionId);
      } else {
         if (sessionId != null) {
            shotList = new LinkedList<>();
            userService.addUser(new UserEntity(sessionId));
         }
      }
   }

   public void addShot(ShotBean shot) {
      double scale = Math.pow(10, 5);
      ShotEntity newShot = new ShotEntity(shot.getX(), Math.ceil(shot.getY() * scale) / scale, shot.getR());
      areaService.checkArea(newShot);
      newShot.setUser(userService.getUserBySessionId(sessionId));
      shotService.addShot(newShot);
      shotList.add(newShot);
      jsonList = shotListJsonParser.fromObjectToJson(shotList);
   }
}
