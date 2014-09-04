package liquibase.ext.ucd.rice;

import liquibase.changelog.ChangeLogParameters;
import liquibase.database.Database;
import liquibase.ext.ucd.BaseTestCase;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ucd/rice/role.changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        liquiBase.update("null");
    }

    // TODO confirm changelog parsing

    @Test
    public void parseAndGenerate() throws Exception {
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


//        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
//                changeLogParameters, resourceAccessor);
//
//        liquiBase.checkLiquibaseTables( false, changeLog, new Contexts() );
//        changeLog.validate(database);
//
//        List<ChangeSet> changeSets = changeLog.getChangeSets();
//
//        List<String> expectedQuery = new ArrayList<String>();
//
//        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");
//        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check1 CHECK(id between 10 and 15) ENABLE");
//        expectedQuery.add("ALTER TABLE LIQUIBASE.addcheck ADD CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");
//
//        ChangeSet changeSet = changeSets.get(1);
//        Change change = changeSet.getChanges().get(0);
//        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
//        assertEquals(expectedQuery.get(0), sql[0].toSql());
    }

}
