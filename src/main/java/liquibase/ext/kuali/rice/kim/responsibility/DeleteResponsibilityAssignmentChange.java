package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.RoleChangeBase;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "deleteResponsibilityAssignment", description = "Deletes the assignment of a responsibility to a given role.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeleteResponsibilityAssignmentChange extends RoleChangeBase {

	protected String documentType;
	protected String routeNode;

	public DeleteResponsibilityAssignmentChange() {}

	public DeleteResponsibilityAssignmentChange( AssignResponsibilityChange change ) {
		documentType = change.getDocumentType();
		routeNode = change.getRouteNode();
		roleNamespaceCode = change.getRoleNamespaceCode();
		roleName = change.getRoleName();
	}

	@Override
	public String getConfirmationMessage() {
		return "DELETED Responsibility Assignment for Document/Route Node" + documentType + " / " + routeNode + " to Role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new DeleteResponsibilityAssignmentStatement(this) };
	}

	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
