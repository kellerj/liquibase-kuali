package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

import org.apache.commons.lang.StringUtils;

public class CreatePermissionGeneratorOracle extends CreatePermissionGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(CreatePermissionStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(CreatePermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"   template_id VARCHAR2(40);\n" +
				"   type_id     VARCHAR2(40);\n" +
				"   perm_id     VARCHAR2(40);\n" +
				"   next_id     NUMBER;\n" +
				"   attr_id     VARCHAR2(40);\n" +
				KimSqlGeneratorHelper.getKimAttributeIdFunctionSql() +
				"BEGIN\n";
		if ( StringUtils.isNotBlank(statement.getId()) ) {
			sql += "        perm_id := '"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getId())+"';\n";
		} else {
			sql += 	"        SELECT KRIM_PERM_ID_S.NEXTVAL INTO next_id FROM dual;\n" +
					"        perm_id := '"+KimSqlGeneratorHelper.makeQuoteSafe(statement.getApplicationId())+"'||next_id;\n";
		}
		sql +=
				"        SELECT PERM_TMPL_ID, kim_typ_id\n" +
				"            INTO template_id, type_id\n" +
				"            FROM KRIM_PERM_TMPL_T\n" +
				"            WHERE nmspc_cd = '"+
				KimSqlGeneratorHelper.makeQuoteSafe(statement.getPermissionTemplateNamespaceCode())+"' AND nm = '"+
				KimSqlGeneratorHelper.makeQuoteSafe(statement.getPermissionTemplateName())+"';\n";
		sql +=
				"        INSERT INTO KRIM_PERM_T(PERM_ID, OBJ_ID, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) \n" +
				"            VALUES(perm_id, SYS_GUID(), template_id, '" 
				+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode() )
				+ "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getName() )
				+ "', '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getDescription() ) + "', 'Y');\n" +
//				"\n" +
//				"        attr_id := get_attribute_id( 'documentTypeName' );\n" +
//				"        INSERT INTO KRIM_RSP_ATTR_DATA_T(ATTR_DATA_ID, OBJ_ID, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) \n" +
//				"            VALUES(rsp_id||'-DOC', SYS_GUID(), rsp_id, type_id, attr_id, '" + KimSqlGeneratorHelper.makeQuoteSafe(statement.getDocumentType() ) + "');\n" +
				"    END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
