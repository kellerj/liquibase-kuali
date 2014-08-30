package liquibase.ext.ucd.rice;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DeleteDataChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

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


	@Override
	public SqlStatement[] generateStatements(Database database) {
		// TODO Auto-generated method stub
		return super.generateStatements(database);
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
