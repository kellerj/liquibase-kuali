package liquibase.ext.ucd.rice;

import liquibase.change.AbstractSQLChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public abstract class KimChangeBase extends AbstractSQLChange {

	@Override
	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		// TODO Auto-generated method stub
		return null;
	}

}
