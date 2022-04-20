package ru.nanikon.third.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * The bean used when transmitting a user request to server and server responce to form
 *
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shot")
@RequestScoped
@Data
@NoArgsConstructor
public class ShotBean {
   private double x;
   private double y;
   private int r;
}
