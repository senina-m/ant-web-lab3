package ru.nanikon.third.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nanikon.third.dao.UserDAO;
import ru.nanikon.third.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Natalia Nikonova
 */
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        userService.setUserDAO(userDAO);
    }

    @Test
    void isUserExist_whenUserNotExist_thenReturnFalse() {
        String sessionId = "test-session-id";
        when(userDAO.findBySessionId(sessionId)).thenReturn(null);
        assertFalse(userService.isUserExists(sessionId));
    }

    @Test
    void isUserExist_whenUserIsExist_thenReturnTrue() {
        String sessionId = "test-session-id";
        when(userDAO.findBySessionId(sessionId)).thenReturn(new UserEntity(sessionId));
        assertTrue(userService.isUserExists(sessionId));
    }

    @Test
    void addUser_whenSaveUserInDB_thenUserSaved() {
        String sessionId = "test-session-id";
        UserEntity newUser = new UserEntity(sessionId);
        userService.addUser(newUser);
        verify(userDAO).save(newUser);
    }

    @Test
    void getUserBySessionId_whenUserIsExist_thenUserJustReturned() {
        String sessionId = "test-session-id";
        UserEntity user = new UserEntity(sessionId);
        when(userDAO.findBySessionId(sessionId)).thenReturn(user);
        UserEntity result = userService.getUserBySessionId(sessionId);
        verify(userDAO, never()).save(user);
        verify(userDAO).findBySessionId(sessionId);
        assertEquals(user, result);
    }

    @Test
    void getUserBySessionId_whenUserNotExist_thenUserSavedAndReturned() {
        String sessionId = "test-session-id";
        int id = 101;
        UserEntity user = new UserEntity(sessionId);
        when(userDAO.findBySessionId(sessionId)).thenReturn(null);
        when(userDAO.save(user)).thenReturn(id);
        UserEntity result = userService.getUserBySessionId(sessionId);
        user.setId(id);
        verify(userDAO).findBySessionId(sessionId);
        verify(userDAO).save(user);
        assertEquals(user, result);
    }
}