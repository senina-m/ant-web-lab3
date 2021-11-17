package ru.nanikon.third.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Natalia Nikonova
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "shots")
public class ShotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private int r;
    @Column(name = "is_hit")
    private boolean hit;

    public ShotEntity(double x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        //this.hit = isHit;
        this.id = (int) Math.round(Math.random() * 1000);
    }
}
