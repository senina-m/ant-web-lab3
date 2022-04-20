package ru.nanikon.third.service;

import lombok.Getter;
import lombok.Setter;
import ru.nanikon.third.dao.UserDAO;
import ru.nanikon.third.entity.UserEntity;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/** Service to work with UserDAO
 *
 * @author Natalia Nikonova
 */
@ManagedBean
@ApplicationScoped
@Getter
@Setter
public class UserService {
    @ManagedProperty(value = "#{userDAO}")
    private UserDAO userDAO;

    public boolean isUserExists(String sessionId) {
        return userDAO.findBySessionId(sessionId) != null;
    }

    public void addUser(UserEntity user) {
        userDAO.save(user);
    }

    public UserEntity getUserBySessionId(String sessionId) {
        UserEntity user = userDAO.findBySessionId(sessionId);
        if (user == null) {
            user = new UserEntity(sessionId);
            int id = userDAO.save(user);
            user.setId(id);
        }
        return user;
    }
}
