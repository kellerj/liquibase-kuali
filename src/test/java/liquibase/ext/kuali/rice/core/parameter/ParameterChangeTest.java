package liquibase.ext.kuali.rice.core.parameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.change.Change;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.RiceTestBase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParameterChangeTest extends RiceTestBase {

	@Override
	protected String getBaseChangeLogPath() {
		return super.getBaseChangeLogPath() + "core/parameter/";
	}

	@Override
	protected String getChangeLogFileName() {
		return "parameter.changelog.test.xml";
	}

    @Test
    public void AAA_testChangelogParsingAndSqlGeneration() throws Exception {
    	Liquibase liquibase = new Liquibase(getChangeLogFile(), new ClassLoaderResourceAccessor(), jdbcConnection);
        Database database = liquibase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        ChangeLogParameters changeLogParameters = new ChangeLogParameters();

        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(getChangeLogFile(), resourceAccessor)
        		.parse(getChangeLogFile(),changeLogParameters, resourceAccessor);
        liquibase.checkLiquibaseTables( false, changeLog, new Contexts() );
        changeLog.validate(database);

        List<ChangeSet> changeSets = changeLog.getChangeSets();

        assertNotNull( "changesets should not have been null", changeSets);
        assertTrue( "should have been at least 1 changeset", changeSets.size() > 0 );
        ChangeSet changeSet = changeSets.get(0);

        assertNotNull( "changeset should not have been null", changeSet);
        assertEquals( "should have been 1 change in changeset 1", 1, changeSet.getChanges().size() );
        Change change = changeSet.getChanges().get(0);

        List<String> expectedQuery = new ArrayList<String>();
        expectedQuery.add("INSERT INTO RICE.KRCR_PARM_T (APPL_ID, NMSPC_CD, CMPNT_CD, PARM_NM, VAL, PARM_TYP_CD, EVAL_OPRTR_CD) VALUES ('KFS', 'KFS-COA', 'Account', 'LB_TEST_1', 'A_VALUE', 'CONFG', 'A')" );
        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
        //System.out.println( sql[0].toSql() );

        Assert.assertEquals( "expected query incorrect", expectedQuery.get(0), sql[0].toSql());
    }

  @Test
  public void BBB_testChangeLogExecution() throws Exception {
	  Liquibase liquibase = new Liquibase(getChangeLogFile(), new ClassLoaderResourceAccessor(), jdbcConnection);
      liquibase.update("null");
  }

  @Test
  public void ZZZ_rollbackChangelogExecution() throws Exception {
	  Liquibase liquibase = new Liquibase(getChangeLogFile(), new ClassLoaderResourceAccessor(), jdbcConnection);
      ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
      ChangeLogParameters changeLogParameters = new ChangeLogParameters();
      DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(getChangeLogFile(), resourceAccessor)
      		.parse(getChangeLogFile(),changeLogParameters, resourceAccessor);
      liquibase.rollback(changeLog.getChangeSets().size(),"null");
  }
}
