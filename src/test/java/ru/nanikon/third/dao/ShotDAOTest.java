package ru.nanikon.third.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Natalia Nikonova
 */

class ShotDAOTest {
    private ShotDAO shotDAO;

    @Test
    void saveShot() {

    }

    @Test
    void findAllBySessionId() {
        UserEntity user = new UserEntity("test-session-id");
        ShotEntity shot = new ShotEntity(user, 2, 2, 2);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(shot);
        transaction.commit();
        session.close();
        shot.setId(id);
        System.out.println(id);
    }
}