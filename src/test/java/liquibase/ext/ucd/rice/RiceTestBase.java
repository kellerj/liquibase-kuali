package liquibase.ext.ucd.rice;

import liquibase.ext.ucd.BaseTestCase;

public abstract class RiceTestBase extends BaseTestCase {

	@Override
	protected String getBaseChangeLogPath() {
		return super.getBaseChangeLogPath() + "rice/";
	}

	@Override
	protected String getLiquibaseConfigFile() {
		return "src/test/resources/" + getBaseChangeLogPath() + "liquibase.properties";
	}
}
