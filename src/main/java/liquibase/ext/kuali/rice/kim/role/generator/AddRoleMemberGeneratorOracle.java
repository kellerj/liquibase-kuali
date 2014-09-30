package liquibase.ext.kuali.rice.kim.role.generator;

import java.util.Map;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.role.statement.AddRoleMemberStatement;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class AddRoleMemberGeneratorOracle extends AddRoleMemberGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(AddRoleMemberStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(AddRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"   role_mbr_id VARCHAR2(40);\n" +
				"   role_id     VARCHAR2(40);\n" +
				"   mbr_id      VARCHAR2(40);\n";
		if ( !statement.getAttributes().isEmpty() ) {
			sql +=  "   type_id     VARCHAR2(40);\n" +
					"   attr_id     VARCHAR2(40);\n" +
					KimSqlGeneratorHelper.getKimAttributeIdFunctionSql() +
					KimSqlGeneratorHelper.getRoleTypeIdFunctionSql();
		}
		if ( statement.getMemberTypeCode().equals("P") ) {
			sql += KimSqlGeneratorHelper.getPrincipalIdFunctionSql();
		} else if ( statement.getMemberTypeCode().equals("G") ) {
			sql += KimSqlGeneratorHelper.getGroupIdFunctionSql();
		}
		sql +=  KimSqlGeneratorHelper.getRoleIdFunctionSql();
		sql +=  "BEGIN\n";
		sql += 	"        SELECT '"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||KRIM_ROLE_MBR_ID_S.NEXTVAL INTO role_mbr_id FROM dual;\n";
		sql +=  "        role_id := get_role_id( '"
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '"
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
		sql += RoleMemberGeneratorHelper.getMemberIdSqlFragment(statement);
		sql +=  "        INSERT INTO KRIM_ROLE_MBR_T(ROLE_MBR_ID, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) \n" +
				"            VALUES(role_mbr_id, SYS_GUID(), role_id, mbr_id, '" + statement.getMemberTypeCode() + "', "
				+ KimSqlGeneratorHelper.toOracleDate(statement.getActiveDate(),"TRUNC(SYSDATE)") + ", "
				+ KimSqlGeneratorHelper.toOracleDate(statement.getInactiveDate()) + ", SYSDATE);\n";
		if ( !statement.getAttributes().isEmpty() ) {
			for ( Map.Entry<String,String> attr : statement.getAttributes().entrySet() ) {
				sql += "        attr_id := get_attribute_id( '" + KimSqlGeneratorHelper.makeQuoteSafe(attr.getKey()) + "' );\n";
				sql += "        type_id := get_role_type_id( '"
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "', '"
						+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "' );\n";
				sql += "        INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T(ATTR_DATA_ID, OBJ_ID, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) \n" +
					   "            VALUES(KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), role_mbr_id, type_id, attr_id, '"
					   + KimSqlGeneratorHelper.makeQuoteSafe(attr.getValue()) + "');\n";
			}
		}
		sql +=  "END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
