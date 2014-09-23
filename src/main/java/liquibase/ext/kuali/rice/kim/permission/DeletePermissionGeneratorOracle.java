package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeletePermissionGeneratorOracle extends DeletePermissionGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeletePermissionStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeletePermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		return new Sql[] {
				new UnparsedSql(
						"DELETE FROM KRIM_PERM_ATTR_DATA_T WHERE PERM_ID = "
						+ "(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "' AND NM = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "') " ),
				new UnparsedSql( "DELETE FROM KRIM_PERM_T WHERE NMSPC_CD = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "' AND NM = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "' " )
		};
	}
}
