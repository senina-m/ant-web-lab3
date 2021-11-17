package com.example.third.bean;

import com.example.third.model.ShotEntity;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shotList")
@SessionScoped
public class ShotListBean {
   @Getter
   @Setter
   private List<ShotEntity> shotList;
}
