package ru.nanikon.third.dao;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.util.HibernateUtil;

import java.io.InputStream;

import static org.dbunit.dataset.filter.DefaultColumnFilter.excludedColumnsTable;

/**
 * @author Natalia Nikonova
 */
class UserDAOTest extends AbstractDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws Exception {
        HibernateUtil.getSessionFactory();
        userDAO = new UserDAO();
        super.setUp();
    }

    @Test
    void findBySessionId_whenUserWithThisSessionIdExist_thenThisUserReturned() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("APP_USERS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("APP_USERS");
        Assertion.assertEquals(expectedTable, actualTable);

        String sessionId = "test1-session-id";
        UserEntity result = userDAO.findBySessionId(sessionId);

        ITable actualData = getConnection()
                .createQueryTable(
                        "result_app_users_findBySessionId_UserExist",
                        "SELECT * FROM app_users WHERE session_id='" + sessionId + "'");
        Assertions.assertEquals(1, actualData.getRowCount());
        Assertions.assertEquals(result.getId(), actualData.getValue(0, "id"));
        Assertions.assertEquals(result.getSessionId(), actualData.getValue(0, "session_id"));
    }

    @Test
    void findBySessionId_whenUserWithThisSessionIdNotExist_thenNullReturned() throws Exception {
        String sessionId = "test-session-id-no-exist";
        UserEntity result = userDAO.findBySessionId(sessionId);
        Assertions.assertNull(result);

        ITable actualData = getConnection()
                .createQueryTable(
                        "result_app_users_findBySessionId_UserNotExist",
                        "SELECT * FROM app_users WHERE session_id='" + sessionId + "'");
        Assertions.assertEquals(0, actualData.getRowCount());
    }

    @Test
    void save_whenUserWithThisSessionIdNotExist_thenSaveUserAndNewIdReturned() throws Exception {
        String sessionId = "test2-session-id";
        UserEntity user = new UserEntity(sessionId);
        int id = userDAO.save(user);
        user.setId(id);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dataset/users-save.xml")) {
            String[] excludedColumns = {"id"};
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = excludedColumnsTable(expectedDataSet.getTable("APP_USERS"), excludedColumns);
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = excludedColumnsTable(databaseDataSet.getTable("APP_USERS"), excludedColumns);
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    void save_whenUserWithThisSessionIdExist_thenNotSaveNewUserAndOldIdReturned() throws Exception {
        String sessionId = "test1-session-id";
        UserEntity user = new UserEntity(sessionId);
        int id = userDAO.save(user);

        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("APP_USERS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("APP_USERS");
        Assertion.assertEquals(expectedTable, actualTable); // проверка что размер таблицы не поменялся - не добавилось новых строчек
        Assertions.assertEquals(id, actualTable.getValue(0, "id")); // что вернулся корректный id уже существующего объекта
    }
}