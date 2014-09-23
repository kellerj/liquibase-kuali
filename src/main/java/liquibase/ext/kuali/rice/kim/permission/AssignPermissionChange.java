package liquibase.ext.kuali.rice.kim.permission;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.RoleChangeBase;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "assignPermission", description = "Assigns a permission to a given role.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class AssignPermissionChange extends RoleChangeBase {

	protected String namespaceCode;
	protected String name;

	@Override
	public String getConfirmationMessage() {
		return "Assigned Permission " + namespaceCode + " / " + name + " to Role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new AssignPermissionStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		return new Change[] { new DeletePermissionAssignmentChange(this) };
	}


	@DatabaseChangeProperty
	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	@DatabaseChangeProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
