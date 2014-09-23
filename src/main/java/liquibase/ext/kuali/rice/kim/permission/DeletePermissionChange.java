package liquibase.ext.kuali.rice.kim.permission;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "deletePermission", description = "Deletes the specified KIM responsibility.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeletePermissionChange extends PermissionChangeBase {

	public DeletePermissionChange() {}

	public DeletePermissionChange( CreatePermissionChange createChange ) {
		setNamespaceCode(createChange.getNamespaceCode());
		setName(createChange.getName());
	}


	@Override
	public String getConfirmationMessage() {
		return "DELETED Permission " + namespaceCode + " / " + name;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new DeletePermissionStatement(this) };
	}

}
