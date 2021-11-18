package ru.nanikon.third.service;

import lombok.Getter;
import lombok.Setter;
import ru.nanikon.third.dao.ShotDAO;
import ru.nanikon.third.entity.ShotEntity;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shotService")
@ApplicationScoped
@Getter
@Setter
public class ShotService {
    @ManagedProperty(value = "#{shotDAO}")
    private ShotDAO shotDAO;

    public List<ShotEntity> getAllBySessionId(String sessionId) {
        return shotDAO.findAllBySessionId(sessionId);
    }

    public void addShot(ShotEntity shot) {
        shotDAO.save(shot);
    }
}
