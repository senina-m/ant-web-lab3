package ru.nanikon.third.dao;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.util.HibernateUtil;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Natalia Nikonova
 */

public class ShotDAOTest extends AbstractDAOTest {
    private ShotDAO shotDAO;
    private UserDAO userDAO;

    public ShotDAOTest() {
        super();
    }

    @BeforeEach
    public void setUp() throws Exception {
        HibernateUtil.getSessionFactory();
        shotDAO = new ShotDAO();
        userDAO = new UserDAO();
        super.setUp();
    }

    @Test
    public void test_findAllBySessionId_whenUserWithThisSessionIdNotExist_thenEmptyListReturned() throws Exception {
        String sessionId = "test-session-id-no-exist";
        List<ShotEntity> result = shotDAO.findAllBySessionId(sessionId);
        List<ShotEntity> wait = new LinkedList<>();
        Assertions.assertEquals(wait, result);

        ITable actualData = getConnection()
                .createQueryTable(
                        "result_app_users_findAllBySessionId_notUserExist",
                        "SELECT * FROM app_users WHERE session_id='" + sessionId + "'");
        Assertions.assertEquals(0, actualData.getRowCount());
    }

    @Test
    public void test_findAllBySessionId_whenUserWithThisSessionIdExistButNoShotsHave_thenEmptyListReturned() throws Exception {
        String sessionId = "test3-session-id";
        List<ShotEntity> result = shotDAO.findAllBySessionId(sessionId);
        List<ShotEntity> wait = new LinkedList<>();
        Assertions.assertEquals(wait, result);

        ITable actualData = getConnection()
                .createQueryTable(
                        "result_app_users_findAllBySessionId_UserExist",
                        "SELECT * FROM app_users WHERE session_id='" + sessionId + "'");
        Assertions.assertEquals(1, actualData.getRowCount());

        actualData = getConnection()
                .createQueryTable(
                        "result_shots_findAllBySessionId_UserExist",
                        "SELECT * FROM shots WHERE user_id=(SELECT id FROM app_users WHERE session_id='" + sessionId + "')");
        Assertions.assertEquals(0, actualData.getRowCount());
    }

    @Test
    public void test_findAllBySessionId_whenUserWithThisSessionIdExistAndShotsHave_thenListShotsReturned() throws Exception {
        String sessionId = "test3-session-id";
        List<ShotEntity> result = shotDAO.findAllBySessionId(sessionId);

        ITable actualData = getConnection()
                .createQueryTable(
                        "result_shots_findAllBySessionId_UserExist",
                        "SELECT * FROM shots WHERE user_id=(SELECT id FROM app_users WHERE session_id='" + sessionId + "')");
        Assertions.assertEquals(result.size(), actualData.getRowCount());
        for (int i = 0; i < result.size(); i++) {
            ShotEntity current = result.get(i);
            Assertions.assertEquals(current.getId(), actualData.getValue(i, "id"));
            Assertions.assertEquals(current.getUser().getId(), actualData.getValue(i, "user_id"));
            Assertions.assertEquals(current.getX(), actualData.getValue(i, "x"));
            Assertions.assertEquals(current.getY(), actualData.getValue(i, "y"));
            Assertions.assertEquals(current.getR(), actualData.getValue(i, "r"));
            Assertions.assertEquals(current.isHit(), actualData.getValue(i, "is_hit"));
        }
    }

    @Test
    public void test_saveShot_whenUserTransient_thenExceptionThrow() throws Exception {
        try {
            UserEntity owner = new UserEntity("test-session-id-not-exist");
            ShotEntity shot = new ShotEntity(owner, 0, 0, 3);
            shot.setHit(true);
            int id = shotDAO.save(shot);
            shot.setId(id);
            Assertions.fail();
        } catch (IllegalStateException ignored) {
            IDataSet expectedDataSet = getDataSet();
            ITable expectedTable = expectedDataSet.getTable("SHOTS");
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("SHOTS");
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void test_saveShots_whenUserPersistent_thenSavedShotAndIdReturned() throws Exception {
        UserEntity owner = userDAO.findBySessionId("test1-session-id");
        ShotEntity shot = new ShotEntity(owner, 0, 0, 3);
        shot.setHit(true);
        int id = shotDAO.save(shot);
        shot.setId(id);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dataset/shots-save.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("SHOTS");
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("SHOTS");
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }
}