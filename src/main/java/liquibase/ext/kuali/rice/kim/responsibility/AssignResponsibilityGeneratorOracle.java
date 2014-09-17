package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class AssignResponsibilityGeneratorOracle extends AssignResponsibilityGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(AssignResponsibilityStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(AssignResponsibilityStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"       role_resp_id NUMBER;\n" +
				"       role_id VARCHAR2(40);\n" +
				"       resp_id VARCHAR2(40);       \n" +
				KimSqlGeneratorHelper.getRoleIdFunctionSql() +
				"    BEGIN\n";
		sql +=
				"        role_id := get_role_id( '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
		sql +=
				"        SELECT rsp_id\n" +
				"            INTO resp_id\n" +
				"            FROM krim_rsp_t r\n" +
				"                WHERE EXISTS ( SELECT 'x' FROM krim_rsp_attr_data_t rd WHERE rd.RSP_ID = r.RSP_ID AND KIM_ATTR_DEFN_ID = '13' AND ATTR_VAL = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getDocumentType()) + "')\n" +
				"                  AND EXISTS ( SELECT 'x' FROM krim_rsp_attr_data_t rd WHERE rd.RSP_ID = r.RSP_ID AND KIM_ATTR_DEFN_ID = '16' AND ATTR_VAL = '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getRouteNode()) + "')\n" +
				"                  AND r.ACTV_IND = 'Y';\n";
		sql +=
				"        SELECT KRIM_ROLE_RSP_ID_S.NEXTVAL INTO role_resp_id FROM dual;\n" +
				"        INSERT INTO KRIM_ROLE_RSP_T(ROLE_RSP_ID, OBJ_ID, ROLE_ID, RSP_ID, ACTV_IND) \n" +
				"            VALUES('"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||role_resp_id, SYS_GUID(), role_id, resp_id, 'Y');\n" +
				"        INSERT INTO KRIM_ROLE_RSP_ACTN_T(ROLE_RSP_ACTN_ID, OBJ_ID, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN) \n" +
				"            VALUES('"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||role_resp_id||'-RRA', SYS_GUID(), "
						+ "'"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getActionTypeCode())+"', NULL, 'F', '*', "
						+ "'"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||role_resp_id, "
						+ "'" + ((statement.isForceAction()?"Y":"N")) + "');\n" +
				"    END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
