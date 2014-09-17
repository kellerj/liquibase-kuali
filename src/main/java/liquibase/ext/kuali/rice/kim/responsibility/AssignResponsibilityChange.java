package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.RoleChangeBase;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "assignResponsibility", description = "Assigns a responsibility to a given role.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class AssignResponsibilityChange extends RoleChangeBase {

	protected String documentType;
	protected String routeNode;
	protected Boolean forceAction;
	protected String actionType;

	@Override
	public String getConfirmationMessage() {
		return "Assigned Responsibility for Document/Route Node" + documentType + " / " + routeNode + " to Role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new AssignResponsibilityStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		// TODO Auto-generated method stub
		return new Change[] { new DeleteResponsibilityAssignmentChange(this) };
	}

	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@DatabaseChangeProperty
	public Boolean getForceAction() {
		return forceAction;
	}

	public void setForceAction(Boolean forceAction) {
		this.forceAction = forceAction;
	}

	@DatabaseChangeProperty
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
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
}
