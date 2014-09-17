package liquibase.ext.kuali.rice.kim;

import org.apache.commons.lang.StringUtils;

public class KimSqlGeneratorHelper {

	private KimSqlGeneratorHelper() {}


	public static String makeQuoteSafe( String value ) {
		return StringUtils.replace(value, "'", "''");
	}

	public static String getKimTypeIdFunctionSql() {
		return  "    FUNCTION get_kim_type_id( TypeNamespace IN VARCHAR2, TypeName IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT kim_typ_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_TYP_T\n" +
				"            WHERE nmspc_cd = TypeNamespace AND nm = TypeName;\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}

	public static String getKimAttributeIdFunctionSql() {
		return "    FUNCTION get_attribute_id( AttributeName IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT KIM_ATTR_DEFN_ID\n" +
				"            INTO id\n" +
				"            FROM KRIM_ATTR_DEFN_T\n" +
				"            WHERE NM = AttributeName AND actv_ind = 'Y';\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}

	public static String getRoleIdFunctionSql() {
		return "    FUNCTION get_role_id( Namespace IN VARCHAR2, Name IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT role_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_ROLE_T\n" +
				"            WHERE nmspc_cd = Namespace AND role_nm = Name;\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}
}
