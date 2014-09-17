package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.statement.AbstractSqlStatement;

public class DeleteResponsibilityAssignmentStatement extends AbstractSqlStatement {

	protected String documentType;
	protected String routeNode;
	protected String roleNamespaceCode;
	protected String roleName;

	public DeleteResponsibilityAssignmentStatement() {}

	public DeleteResponsibilityAssignmentStatement( DeleteResponsibilityAssignmentChange change ) {
		documentType = change.getDocumentType();
		routeNode = change.getRouteNode();
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

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getRouteNode() {
		return routeNode;
	}

	public void setRouteNode(String routeNode) {
		this.routeNode = routeNode;
	}
}
