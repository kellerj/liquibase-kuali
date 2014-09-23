package liquibase.ext.kuali.rice.kim.permission;

import liquibase.statement.AbstractSqlStatement;

public class DeletePermissionAssignmentStatement extends AbstractSqlStatement {

	protected String namespaceCode;
	protected String name;
	protected String roleNamespaceCode;
	protected String roleName;

	public DeletePermissionAssignmentStatement() {}

	public DeletePermissionAssignmentStatement( DeletePermissionAssignmentChange change ) {
		name = change.getName();
		namespaceCode = change.getNamespaceCode();
		roleNamespaceCode = change.getRoleNamespaceCode();
		roleName = change.getRoleName();
	}


	public String getRoleNamespaceCode() {
		return roleNamespaceCode;
	}

	public void setRoleNamespaceCode(String roleNamespaceCode) {
		this.roleNamespaceCode = roleNamespaceCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
