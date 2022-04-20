package ru.nanikon.third.entity;

import lombok.AllArgsConstructor;
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
import java.io.Serializable;

/** Entity describing the shot: the user's request and the result of its processing
 *
 * @author Natalia Nikonova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shots")
public class ShotEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public ShotEntity(UserEntity owner, double x, double y, int r) {
        this.user = owner;
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
