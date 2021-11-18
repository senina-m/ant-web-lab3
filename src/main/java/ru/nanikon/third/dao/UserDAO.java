package ru.nanikon.third.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.util.HibernateUtil;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "userDAO")
@ApplicationScoped
@Getter
@Setter
public class UserDAO {
   public UserEntity findById(int id) {
      Session session = HibernateUtil.getSessionFactory().openSession();
      UserEntity user = session.get(UserEntity.class, id);
      session.close();
      return user;
   }

   public UserEntity findBySessionId(String sessionId) {
      Session session = HibernateUtil.getSessionFactory().openSession();
      Query<UserEntity> query = session.createQuery("from UserEntity where sessionId = :sessionId").setParameter("sessionId", sessionId);
      UserEntity user = query.list().get(0);
      session.close();
      return user;
   }

   public void save(UserEntity user) {
      Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.save(user);
      transaction.commit();
      session.close();
   }
}
