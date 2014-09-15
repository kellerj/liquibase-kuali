package liquibase.ext.kuali.rice.kim.role;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DeleteDataChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

import org.apache.commons.lang.StringUtils;

@DatabaseChange(name="createRole", description = "Creates a KIM role of the given type.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreateRoleChange extends RoleChangeBase {

	protected String roleTypeNamespace;
	protected String roleTypeName;
	protected String roleDescription;
	protected String roleId;

	@Override
	public String getConfirmationMessage() {
		return "Created Role " + roleNamespaceCode + " / " + roleName;
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

	@Override
	public SqlStatement[] generateStatements(Database database) {
		String sql = "DECLARE \n" +
				"   type_id VARCHAR2(40);\n" +
				"   role_id VARCHAR2(40);\n" +
				"   next_id NUMBER;\n" +
				getKimTypeIdFunctionSql() +
				"BEGIN\n" +
				"    type_id := get_kim_type_id( '"+makeQuoteSafe(roleTypeNamespace)+"', '"+makeQuoteSafe(roleTypeName)+"' );\n";
		if ( StringUtils.isNotBlank(roleId) ) {
			sql += "        role_id := '"+makeQuoteSafe(roleId)+"';\n";
		} else {
			sql += 	"        SELECT KRIM_ROLE_ID_S.NEXTVAL INTO next_id FROM dual;\n" +
					"        role_id := '"+makeQuoteSafe(applicationId)+"'||next_id;\n";
		}
		sql +=
				"    INSERT INTO KRIM_ROLE_T\n" +
				"        (ROLE_ID, OBJ_ID, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) \n" +
				"        VALUES(role_id, SYS_GUID(), '"+makeQuoteSafe(roleName)+"', '"+makeQuoteSafe(roleNamespaceCode)+"', '"+makeQuoteSafe(roleDescription)+"', type_id, 'Y', SYSDATE);\n" +
				"END;";
		RawSqlStatement statement = new RawSqlStatement(sql,"/");
		return new SqlStatement[] { statement };
	}

	@Override
	protected Change[] createInverses() {
		DeleteDataChange deleteDataChange = new DeleteDataChange();
		deleteDataChange.setTableName("KRIM_ROLE_T");
		deleteDataChange.setWhere( "NMSPC_CD = '" + roleNamespaceCode + "' AND ROLE_NM = '" + roleName + "'" );

		return new Change[] { deleteDataChange };
	}


	@DatabaseChangeProperty
	public String getRoleTypeNamespace() {
		return roleTypeNamespace;
	}

	public void setRoleTypeNamespace(String roleTypeNamespace) {
		this.roleTypeNamespace = roleTypeNamespace;
	}

	@DatabaseChangeProperty
	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	@DatabaseChangeProperty
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@DatabaseChangeProperty
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
