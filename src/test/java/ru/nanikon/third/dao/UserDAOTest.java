package ru.nanikon.third.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Natalia Nikonova
 */
class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void findBySessionId_whenUserWithThisSessionIdExist_thenThisUserReturned() {
        String sessionId = "test1-session-id";
        UserEntity user = new UserEntity(sessionId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(user);
        transaction.commit();
        session.close();
        user.setId(id);
        UserEntity result = userDAO.findBySessionId(sessionId);
        assertEquals(user, result);
    }

    @Test
    void findBySessionId_whenUserWithThisSessionIdNotExist_thenNullReturned() {
        String sessionId = "test2-session-id";
        UserEntity result = userDAO.findBySessionId(sessionId);
        assertNull(result);
    }

    @Test
    void save_whenUserWithThisSessionIdExist_thenNotSaveNewUserAndOldIdReturned() {
        String sessionId = "test-session-id";
        UserEntity user = new UserEntity(sessionId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id1 = (int) session.save(user);
        transaction.commit();
        session.close();
        int id2 = userDAO.save(user);
        assertEquals(id1, id2);
    }

    @Test
    void save_whenUserWithThisSessionIdNotExist_thenSaveUserAndNewIdReturned() {

    }
}