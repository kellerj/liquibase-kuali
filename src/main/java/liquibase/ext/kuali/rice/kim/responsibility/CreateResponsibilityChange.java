package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "createResponsibility", description = "Creates a KIM responsibility for the given document and route node.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreateResponsibilityChange extends ResponsibilityChangeBase {

	// PROCEDURE create_responsibility( ResponsibilityId IN VARCHAR2,
	// ResponsibilityNamespace IN VARCHAR2, ResponsibilityName IN VARCHAR2,
	// DocumentType IN VARCHAR2, RouteNode IN VARCHAR2, RequiredFlag IN CHAR,
	// ResponsibilityDescription IN VARCHAR2 ) IS
	protected String description;
	protected String responsibilityId;
	protected String documentType;
	protected String routeNode;
	protected Boolean routingRequired;
	protected Boolean actionDetailsAtRowMemberLevel;

	@Override
	public String getConfirmationMessage() {
		return "Created Responsibility " + namespaceCode + " / " + name + " for Document/Route Node: " + documentType + " / " + routeNode;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new CreateResponsibilityStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		DeleteResponsibilityChange rollbackChange = new DeleteResponsibilityChange( this );
		return new Change[] { rollbackChange };
	}

	@DatabaseChangeProperty
	public String getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(String responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	@DatabaseChangeProperty
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@DatabaseChangeProperty
	public String getRouteNode() {
		return routeNode;
	}

	public void setRouteNode(String routeNode) {
		this.routeNode = routeNode;
	}

	@DatabaseChangeProperty
	public Boolean getRoutingRequired() {
		return routingRequired;
	}

	public void setRoutingRequired(Boolean routingRequired) {
		this.routingRequired = routingRequired;
	}

	@DatabaseChangeProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@DatabaseChangeProperty
	public Boolean getActionDetailsAtRowMemberLevel() {
		return actionDetailsAtRowMemberLevel;
	}

	public void setActionDetailsAtRowMemberLevel(
			Boolean actionDetailsAtRowMemberLevel) {
		this.actionDetailsAtRowMemberLevel = actionDetailsAtRowMemberLevel;
	}
}
