package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.statement.AbstractSqlStatement;

public class CreateResponsibilityStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String name;
	protected String id;
	protected String documentType;
	protected String routeNode;
	protected boolean routingRequired;
	protected boolean actionDetailsAtRowMemberLevel;
	protected String description;

	public CreateResponsibilityStatement() {
	}

	public CreateResponsibilityStatement( CreateResponsibilityChange change ) {
		applicationId = change.getApplicationId();
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
		id = change.getResponsibilityId();
		documentType = change.getDocumentType();
		routeNode = change.getRouteNode();
		description = change.getDescription();
		if ( change.getRoutingRequired() != null ) {
			routingRequired = change.getRoutingRequired();
		}
		if ( change.getActionDetailsAtRowMemberLevel() != null ) {
			actionDetailsAtRowMemberLevel = change.getActionDetailsAtRowMemberLevel();
		}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public boolean isRoutingRequired() {
		return routingRequired;
	}
	public void setRoutingRequired(boolean routingRequired) {
		this.routingRequired = routingRequired;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public boolean isActionDetailsAtRowMemberLevel() {
		return actionDetailsAtRowMemberLevel;
	}

	public void setActionDetailsAtRowMemberLevel(
			boolean actionDetailsAtRowMemberLevel) {
		this.actionDetailsAtRowMemberLevel = actionDetailsAtRowMemberLevel;
	}

}
