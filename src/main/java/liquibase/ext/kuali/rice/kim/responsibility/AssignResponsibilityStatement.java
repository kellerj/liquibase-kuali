package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.statement.AbstractSqlStatement;

public class AssignResponsibilityStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String documentType;
	protected String routeNode;
	protected String roleNamespaceCode;
	protected String roleName;
	protected boolean forceAction;
	protected String actionTypeCode;

	public AssignResponsibilityStatement() {}

	public AssignResponsibilityStatement( AssignResponsibilityChange change ) {
		applicationId = change.getApplicationId();
		documentType = change.getDocumentType();
		routeNode = change.getRouteNode();
		roleNamespaceCode = change.getRoleNamespaceCode();
		roleName = change.getRoleName();
		if ( change.getForceAction() != null ) {
			forceAction = change.getForceAction();
		}
		actionTypeCode = "A";
		if ( change.getActionType() != null ) {
			switch ( change.getActionType() ) {
				case "APPROVE" :
					actionTypeCode = "A";
					break;
				case "ACKNOWLEDGE" :
					actionTypeCode = "K";
					break;
				case "FYI" :
					actionTypeCode = "F";
					break;
			}
		}
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


	public boolean isForceAction() {
		return forceAction;
	}

	public void setForceAction(boolean forceAction) {
		this.forceAction = forceAction;
	}

	public String getActionTypeCode() {
		return actionTypeCode;
	}

	public void setActionTypeCode(String actionTypeCode) {
		this.actionTypeCode = actionTypeCode;
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

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
