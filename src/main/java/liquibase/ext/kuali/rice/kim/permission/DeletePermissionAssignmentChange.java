package liquibase.ext.kuali.rice.kim.permission;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.RoleChangeBase;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "deletePermissionAssignment", description = "Deletes the assignment of a permission to a given role.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeletePermissionAssignmentChange extends RoleChangeBase {

	protected String namespaceCode;
	protected String name;

	public DeletePermissionAssignmentChange() {}

	public DeletePermissionAssignmentChange( AssignPermissionChange change ) {
		name = change.getName();
		namespaceCode = change.getNamespaceCode();
		roleNamespaceCode = change.getRoleNamespaceCode();
		roleName = change.getRoleName();
	}

	@Override
	public String getConfirmationMessage() {
		return "DELETED Permission Assignment for " + namespaceCode + " / " + name + " to Role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new DeletePermissionAssignmentStatement(this) };
	}

	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@DatabaseChangeProperty
	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	@DatabaseChangeProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
