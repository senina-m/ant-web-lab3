package ru.nanikon.third.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * @author Natalia Nikonova
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "app_users",
        uniqueConstraints = @UniqueConstraint(columnNames = "session_id"))
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "session_id")
    private String sessionId;

    public UserEntity(String sessionId) {
        this.sessionId = sessionId;
    }
}
