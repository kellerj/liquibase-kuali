package liquibase.ext.kuali.rice.kim;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChangeProperty;

import org.apache.commons.lang.StringUtils;

public abstract class KimChangeBase extends AbstractChange {

	protected String applicationId;

	@DatabaseChangeProperty(description="Application which owns the new KIM data", exampleValue="KFS")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	protected String makeQuoteSafe( String value ) {
		return StringUtils.replace(value, "'", "''");
	}

	protected String getKimTypeIdFunctionSql() {
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

	protected String getKimAttributeIdFunctionSql() {
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

}
