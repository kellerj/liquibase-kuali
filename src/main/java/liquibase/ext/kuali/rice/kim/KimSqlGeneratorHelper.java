package liquibase.ext.kuali.rice.kim;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class KimSqlGeneratorHelper {

	private KimSqlGeneratorHelper() {}


	public static String makeQuoteSafe( String value ) {
		if ( value == null ) {
			return "";
		}
		return StringUtils.replace(value, "'", "''");
	}

	public static String toOracleDate( Date date ) {
		return toOracleDate(date, "NULL");
	}

	public static String toOracleDate( Date date, String defaultExpression ) {
		if ( date == null ) {
			return defaultExpression;
		}
		return "TO_DATE( '" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "', 'YYYY-MM-DD' )";
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

	public static String getGroupIdFunctionSql() {
		return "    FUNCTION get_group_id( Namespace IN VARCHAR2, Name IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT grp_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_GRP_T\n" +
				"            WHERE nmspc_cd = Namespace AND grp_nm = Name;\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}

	public static String getPrincipalIdFunctionSql() {
		return "    FUNCTION get_principal_id( PrincipalName IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT prncpl_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_PRNCPL_T\n" +
				"            WHERE prncpl_nm = PrincipalName;\n" +
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

	public static String getRoleTypeIdFunctionSql() {
		return "    FUNCTION get_role_type_id( Namespace IN VARCHAR2, Name IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT kim_typ_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_ROLE_T\n" +
				"            WHERE nmspc_cd = Namespace AND role_nm = Name;\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}

	public static String getPermissionIdFunctionSql() {
		return "    FUNCTION get_permission_id( Namespace IN VARCHAR2, Name IN VARCHAR2 ) RETURN VARCHAR2 IS\n" +
				"        id VARCHAR2(40);\n" +
				"    BEGIN\n" +
				"        SELECT perm_id\n" +
				"            INTO id\n" +
				"            FROM KRIM_PERM_T\n" +
				"            WHERE nmspc_cd = Namespace AND nm = Name;\n" +
				"        RETURN id;\n" +
				"    END;\n";
	}
}
