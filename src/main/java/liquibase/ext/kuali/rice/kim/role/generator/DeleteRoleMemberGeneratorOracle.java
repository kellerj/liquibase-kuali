package liquibase.ext.kuali.rice.kim.role.generator;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.role.statement.DeleteRoleMemberStatement;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeleteRoleMemberGeneratorOracle extends DeleteRoleMemberGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeleteRoleMemberStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeleteRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"   type_id     VARCHAR2(40);\n" +
				"   next_id     NUMBER;\n" +
				"   attr_id     VARCHAR2(40);\n" +
				KimSqlGeneratorHelper.getKimAttributeIdFunctionSql() +
				"BEGIN\n";
//		if ( StringUtils.isNotBlank(statement.getTypeId()) ) {
//			sql += "        type_id := '"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getTypeId())+"';\n";
//		} else {
//			sql += 	"        SELECT KRIM_TYP_ID_S.NEXTVAL INTO next_id FROM dual;\n" +
//					"        type_id := '"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||next_id;\n";
//		}
//		sql +=
//				"        INSERT INTO KRIM_TYP_T(KIM_TYP_ID, OBJ_ID, NMSPC_CD, NM, SRVC_NM, ACTV_IND) \n" +
//				"            VALUES(type_id, SYS_GUID(), '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode() )
//				+ "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName() )
//				+ "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getServiceName() ) + "', 'Y');\n\n";
//		char sortCode = 'a';
//		for ( String attributeName : statement.getAttributeNames() ) {
//			sql +=
//					"        attr_id := get_attribute_id( '" + KimSqlGeneratorHelper.makeQuoteSafe(attributeName) + "' );\n" +
//					//"        SELECT KRIM_TYP_ATTR_ID_S.NEXTVAL INTO next_id FROM dual;\n" +
//					"        INSERT INTO KRIM_TYP_ATTR_T ( KIM_TYP_ATTR_ID, OBJ_ID, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND) \n" +
//					"            VALUES(type_id||'-" + sortCode + "', SYS_GUID(), '" + sortCode + "', type_id, attr_id, 'Y');\n\n";
//			sortCode++;
//		}
		sql +=  "NULL; END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
