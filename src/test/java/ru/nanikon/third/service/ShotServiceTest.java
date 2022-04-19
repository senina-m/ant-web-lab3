package ru.nanikon.third.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nanikon.third.dao.ShotDAO;
import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Natalia Nikonova
 */
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ShotServiceTest {
    private ShotService shotService;

    @Mock
    private ShotDAO shotDAO;

    @Before
    public void setUp() {
        shotService = new ShotService();
        shotService.setShotDAO(shotDAO);
    }

    @Test
    public void getAllBySessionId_whenNoShotWithThisSessionId_thenEmptyListReturned() {
        String sessionId = "test-session-id";
        List<ShotEntity> wait = new LinkedList<>();
        when(shotDAO.findAllBySessionId(sessionId)).thenReturn(wait);
        List<ShotEntity> result = shotService.getAllBySessionId(sessionId);
        verify(shotDAO).findAllBySessionId(sessionId);
        assertEquals(result, wait);
    }

    @Test
    public void getAllBySessionId_whenExistShotWithThisSessionId_thenTheyInListReturned() {
        String sessionId = "test-session-id";
        UserEntity owner = new UserEntity(sessionId);
        ShotEntity shot = new ShotEntity(owner, 1, 1, 2);
        List<ShotEntity> wait = new LinkedList<>();
        wait.add(shot);
        when(shotDAO.findAllBySessionId(sessionId)).thenReturn(wait);
        List<ShotEntity> result = shotService.getAllBySessionId(sessionId);
        verify(shotDAO).findAllBySessionId(sessionId);
        assertEquals(result, wait);
    }

    @Test
    public void addShot_whenSaveShotInDB_thenShotSaved() {
        ShotEntity shot = new ShotEntity();
        shotService.addShot(shot);
        verify(shotDAO).save(shot);
    }
}