package com.example.third.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shot")
@RequestScoped
@Data
@NoArgsConstructor
public class ShotBean {
   private double x;
   private double y;
   private int r = 5;
}
