package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class AssignPermissionGeneratorOracle extends AssignPermissionGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(AssignPermissionStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(AssignPermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"       role_perm_id NUMBER;\n" +
				"       role_id VARCHAR2(40);\n" +
				"       perm_id VARCHAR2(40);       \n" +
				KimSqlGeneratorHelper.getRoleIdFunctionSql() +
				KimSqlGeneratorHelper.getPermissionIdFunctionSql() +
				"    BEGIN\n";
		sql +=
				"        role_id := get_role_id( '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
		sql +=
				"        perm_id := get_permission_id( '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode()) + "', '" 
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getName()) + "' );\n";
		sql +=
				"        SELECT KRIM_ROLE_PERM_ID_S.NEXTVAL INTO role_perm_id FROM dual;\n" +
				"        INSERT INTO KRIM_ROLE_PERM_T(ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) \n" +
				"            VALUES('"
				+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||role_perm_id, SYS_GUID(), role_id, perm_id, 'Y');\n" +
				"    END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
