package liquibase.ext.kuali.rice.kim.permission;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "createPermission", description = "Creates a KIM permission", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreatePermissionChange extends PermissionChangeBase {

	protected String description;
	protected String permissionId;
	protected String permissionTemplateNamespaceCode;
	protected String permissionTemplateName;

	@Override
	public String getConfirmationMessage() {
		return "Created Permission " + namespaceCode + " / " + name;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new CreatePermissionStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		DeletePermissionChange rollbackChange = new DeletePermissionChange( this );
		return new Change[] { rollbackChange };
	}

	@DatabaseChangeProperty
	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String responsibilityId) {
		this.permissionId = responsibilityId;
	}

	@DatabaseChangeProperty
	public String getPermissionTemplateNamespaceCode() {
		return permissionTemplateNamespaceCode;
	}

	public void setPermissionTemplateNamespaceCode(
			String permissionTemplateNamespaceCode) {
		this.permissionTemplateNamespaceCode = permissionTemplateNamespaceCode;
	}

	@DatabaseChangeProperty
	public String getPermissionTemplateName() {
		return permissionTemplateName;
	}

	public void setPermissionTemplateName(String permissionTemplateName) {
		this.permissionTemplateName = permissionTemplateName;
	}

	@DatabaseChangeProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
