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
import javax.persistence.PersistenceException;

/**
 * @author Natalia Nikonova
 */
@ManagedBean(name = "userDAO")
@ApplicationScoped
@Getter
@Setter
public class UserDAO {
   public UserEntity findBySessionId(String sessionId) {
      UserEntity user = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         Query<UserEntity> query = session.createQuery("from UserEntity where sessionId = :sessionId").setParameter("sessionId", sessionId);
         user = query.list().get(0);
      } catch (IndexOutOfBoundsException ignored) {
      }
      return user;
   }

   public int save(UserEntity user) {
      int id;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         Transaction transaction = session.beginTransaction();
         id = (int) session.save(user);
         System.out.println("user id " + id + user.getSessionId());
         transaction.commit();
      } catch (PersistenceException e) {
         id = findBySessionId(user.getSessionId()).getId();
      }
      return id;
   }
}
