package liquibase.ext.kuali;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

import org.junit.After;
import org.junit.Before;

/*
 * Class used by tests to set up connection and clean database.
 */
public abstract class BaseTestCase {

    private static String url;
    private static Driver driver;
    private static Properties info;
    protected Connection connection;
    protected JdbcConnection jdbcConnection;
    //protected static Liquibase liquiBase;

    protected String getBaseChangeLogPath() {
    	return "liquibase/ext/kuali/";
    }

    protected abstract String getChangeLogFileName();

    protected String getLiquibaseConfigFile() {
		return "src/test/resources/liquibase.properties";
    }

    protected String getChangeLogFile() {
    	return getBaseChangeLogPath()+getChangeLogFileName();
    }

    private void extractConnectionInformation() throws Exception {
        info = new Properties();
        info.load(new FileInputStream(getLiquibaseConfigFile()));

        url = info.getProperty("url");
        driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true,
                Thread.currentThread().getContextClassLoader()).newInstance();
    }

    @Before
    public void connectToDB() throws Exception {
        if (info == null) {
        	extractConnectionInformation();
        }
        if ( jdbcConnection == null ) {
	        connection = driver.connect(url, info);

	        if (connection == null) {
	            throw new DatabaseException("Connection could not be created to " + url + " with driver "
	                    + driver.getClass().getName() + ".  Possibly the wrong driver for the given database URL");
	        }

	        jdbcConnection = new JdbcConnection(connection);
        }
    }

    @After
    public void tearDown() throws Exception {
    	if ( jdbcConnection != null ) {
    		jdbcConnection.close();
	    	connection = null;
	    	jdbcConnection = null;
    	}
    }

//    public static void cleanDB() throws Exception {
//
//        liquiBase.dropAll();
//        LockServiceFactory.reset();
//    }
}
