package liquibase.ext.kuali.rice.kim.type;

import java.util.List;

import liquibase.Contexts;
import liquibase.LabelExpression;
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
import liquibase.statement.SqlStatement;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KimTypeChangeTest extends RiceTestBase {

	@Override
	protected String getBaseChangeLogPath() {
		return super.getBaseChangeLogPath() + "kim/type/";
	}

	@Override
	protected String getChangeLogFileName() {
		return "kimtype.changelog.test.xml";
	}

    @Test
    public void AAA_testChangelogParsingAndSqlGeneration() throws Exception {
    	Liquibase liquibase = new Liquibase(getChangeLogFile(), new ClassLoaderResourceAccessor(), jdbcConnection);
        Database database = liquibase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(getChangeLogFile(), resourceAccessor)
        		.parse(getChangeLogFile(),changeLogParameters, resourceAccessor);
        liquibase.checkLiquibaseTables( false, changeLog, new Contexts(), new LabelExpression() );
        changeLog.validate(database);

        List<ChangeSet> changeSets = changeLog.getChangeSets();
//
//        List<String> expectedQuery = new ArrayList<String>();
//
//        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");
//        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check1 CHECK(id between 10 and 15) ENABLE");
//        expectedQuery.add("ALTER TABLE LIQUIBASE.addcheck ADD CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");
//
        for ( ChangeSet changeSet : changeSets ) {
        	System.out.println( "***************************************************************" );
        	System.out.println( "Dumping SQL from Changeset: " + changeSet.getId() );
        	System.out.println( "***************************************************************" );
        	for ( Change change : changeSet.getChanges() ) {
        		SqlStatement[] statements = change.generateStatements(database);
        		for ( SqlStatement statement : statements ) {
	                Sql[] sqls = SqlGeneratorFactory.getInstance().generateSql(statement, database);
	                for ( Sql sql : sqls ) {
	                	System.out.println( sql.toSql() );
	                }
        		}
        	}
        }

        //assertEquals(expectedQuery.get(0), sql[0].toSql());
    }

	@Test
	public void BBB_testChangeLogExecution() throws Exception {
		Liquibase liquibase = new Liquibase(getChangeLogFile(),
				new ClassLoaderResourceAccessor(), jdbcConnection);
		liquibase.update("null");
	}

	@Test
	public void ZZZ_rollbackChangelogExecution() throws Exception {
		Liquibase liquibase = new Liquibase(getChangeLogFile(),
				new ClassLoaderResourceAccessor(), jdbcConnection);
		ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
		ChangeLogParameters changeLogParameters = new ChangeLogParameters();
		DatabaseChangeLog changeLog = ChangeLogParserFactory
				.getInstance()
				.getParser(getChangeLogFile(), resourceAccessor)
				.parse(getChangeLogFile(), changeLogParameters,
						resourceAccessor);
		liquibase.rollback(changeLog.getChangeSets().size(), "null");
	}

/*
 * Cleanup SQL
 *
delete from DATABASECHANGELOGLOCK
/
delete from DATABASECHANGELOG
WHERE id LIKE 'KimTypeWith%'
/
delete from KRIM_TYP_ATTR_T WHERE KIM_TYP_ID IN (SELECT KIM_TYP_ID from KRIM_TYP_T WHERE nm IN ( 'Another Role', 'Organization-Based Derived Role' ) )
/
delete from KRIM_TYP_T WHERE nm IN ( 'Another Role', 'Organization-Based Derived Role' )
/
 */
}
