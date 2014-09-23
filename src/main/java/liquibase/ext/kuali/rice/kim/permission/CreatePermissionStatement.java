package liquibase.ext.kuali.rice.kim.permission;

import liquibase.statement.AbstractSqlStatement;

public class CreatePermissionStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String name;
	protected String id;
	protected String description;
	protected String permissionTemplateNamespaceCode;
	protected String permissionTemplateName;

	public CreatePermissionStatement() {
	}

	public CreatePermissionStatement( CreatePermissionChange change ) {
		applicationId = change.getApplicationId();
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
		id = change.getPermissionId();
		description = change.getDescription();
		permissionTemplateName = change.getPermissionTemplateName();
		permissionTemplateNamespaceCode = change.getPermissionTemplateNamespaceCode();
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

	public String getPermissionTemplateNamespaceCode() {
		return permissionTemplateNamespaceCode;
	}

	public void setPermissionTemplateNamespaceCode(
			String permissionTemplateNamespaceCode) {
		this.permissionTemplateNamespaceCode = permissionTemplateNamespaceCode;
	}

	public String getPermissionTemplateName() {
		return permissionTemplateName;
	}

	public void setPermissionTemplateName(String permissionTemplateName) {
		this.permissionTemplateName = permissionTemplateName;
	}

}
