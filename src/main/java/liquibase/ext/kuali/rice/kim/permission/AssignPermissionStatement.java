package liquibase.ext.kuali.rice.kim.permission;

import liquibase.statement.AbstractSqlStatement;

public class AssignPermissionStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String name;
	protected String roleNamespaceCode;
	protected String roleName;

	public AssignPermissionStatement() {}

	public AssignPermissionStatement( AssignPermissionChange change ) {
		applicationId = change.getApplicationId();
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

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
