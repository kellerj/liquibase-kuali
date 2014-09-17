package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeleteResponsibilityGeneratorOracle extends DeleteResponsibilityGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeleteResponsibilityStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeleteResponsibilityStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		return new Sql[] {
				new UnparsedSql(
						"DELETE FROM KRIM_RSP_ATTR_DATA_T WHERE RSP_ID = "
						+ "(SELECT RSP_ID FROM KRIM_RSP_T WHERE NMSPC_CD = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "' AND NM = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "') " ),
				new UnparsedSql( "DELETE FROM KRIM_RSP_T WHERE NMSPC_CD = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "' AND NM = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "' " )
		};
	}
}
