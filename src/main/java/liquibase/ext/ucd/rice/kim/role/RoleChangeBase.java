package liquibase.ext.ucd.rice.kim.role;

import liquibase.change.DatabaseChangeProperty;
import liquibase.ext.ucd.rice.kim.KimChangeBase;

public abstract class RoleChangeBase extends KimChangeBase {

	protected String roleNamespaceCode;
	protected String roleName;

	@DatabaseChangeProperty(description="Namespace code for the role to be modified", exampleValue="KFS-SYS")
	public String getRoleNamespaceCode() {
		return roleNamespaceCode;
	}

	public void setRoleNamespaceCode(String roleNamespaceCode) {
		this.roleNamespaceCode = roleNamespaceCode;
	}

	@DatabaseChangeProperty(description="Name of the role to be modified", exampleValue="Chart Manager")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
