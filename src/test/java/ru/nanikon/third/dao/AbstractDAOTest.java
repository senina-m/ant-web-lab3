package ru.nanikon.third.dao;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author Natalia Nikonova
 */
public class AbstractDAOTest extends DataSourceBasedDBTestCase {
    /**
     * Returns the test dataset.
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return new FlatXmlDataSetBuilder()
                        .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("dataset/init.xml"));
    }

    /**
     * Returns the test DataSource.
     */
    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test");
        dataSource.setUser("user");
        dataSource.setPassword("user");
        return dataSource;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
}
