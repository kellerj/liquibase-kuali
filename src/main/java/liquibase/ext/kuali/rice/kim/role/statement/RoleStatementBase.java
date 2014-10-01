package liquibase.ext.kuali.rice.kim.role.statement;

import liquibase.statement.AbstractSqlStatement;

public abstract class RoleStatementBase extends AbstractSqlStatement {

	protected String applicationId;
	protected String roleNamespaceCode;
	protected String roleName;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
}
