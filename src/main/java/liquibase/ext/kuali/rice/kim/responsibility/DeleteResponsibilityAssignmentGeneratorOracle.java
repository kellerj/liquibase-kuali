package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeleteResponsibilityAssignmentGeneratorOracle extends DeleteResponsibilityAssignmentGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeleteResponsibilityAssignmentStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeleteResponsibilityAssignmentStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"       xrole_id VARCHAR2(40);\n" +
				"       resp_id  VARCHAR2(40);\n" +
				"       rr_id    VARCHAR2(40);\n" +
				KimSqlGeneratorHelper.getRoleIdFunctionSql() +
				"    BEGIN\n";
		sql +=
				"        xrole_id := get_role_id( '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
		sql +=
				"        SELECT rsp_id\n" +
				"            INTO resp_id\n" +
				"            FROM krim_rsp_t r\n" +
				"                WHERE EXISTS ( SELECT 'x' FROM krim_rsp_attr_data_t rd WHERE rd.RSP_ID = r.RSP_ID AND KIM_ATTR_DEFN_ID = '13' AND ATTR_VAL = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getDocumentType()) + "')\n" +
				"                  AND EXISTS ( SELECT 'x' FROM krim_rsp_attr_data_t rd WHERE rd.RSP_ID = r.RSP_ID AND KIM_ATTR_DEFN_ID = '16' AND ATTR_VAL = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRouteNode()) + "')\n" +
				"                  AND r.ACTV_IND = 'Y';\n";
		sql +=
				"        SELECT role_rsp_id INTO rr_id FROM KRIM_ROLE_RSP_T WHERE rsp_id = resp_id AND role_id = xrole_id;\n";
		sql +=
				"        DELETE FROM KRIM_ROLE_RSP_ACTN_T WHERE ROLE_RSP_ACTN_ID = rr_id||'-RRA';\n" +
				"        DELETE FROM KRIM_ROLE_RSP_T WHERE role_rsp_id = rr_id;\n" +
				"    END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
