package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeletePermissionAssignmentGeneratorOracle extends DeletePermissionAssignmentGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeletePermissionAssignmentStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeletePermissionAssignmentStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"       xrole_id VARCHAR2(40);\n" +
				"       xperm_id  VARCHAR2(40);\n" +
				KimSqlGeneratorHelper.getRoleIdFunctionSql() +
				KimSqlGeneratorHelper.getPermissionIdFunctionSql() +
				"    BEGIN\n";
		sql +=
				"        xrole_id := get_role_id( '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
		sql +=
				"        xperm_id := get_permission_id( '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "', '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "' );\n";
		sql +=
				"        DELETE FROM KRIM_ROLE_PERM_T WHERE perm_id = xperm_id AND role_id = xrole_id;\n" +
				"    END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
