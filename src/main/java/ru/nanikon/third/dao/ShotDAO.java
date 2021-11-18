package ru.nanikon.third.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.util.HibernateUtil;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "shotDAO")
@ApplicationScoped
@Getter
@Setter
public class ShotDAO {
    public void save(ShotEntity shot) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(shot);
        transaction.commit();
        session.close();
    }

    public List<ShotEntity> findAllBySessionId(String sessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<ShotEntity> query = session.createQuery("from ShotEntity where user.sessionId = :sessionId")
                .setParameter("sessionId", sessionId);
        return query.getResultList();
    }
}
