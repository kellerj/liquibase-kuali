package liquibase.ext.kuali.rice.kim.permission;

import liquibase.statement.AbstractSqlStatement;

public class DeletePermissionStatement extends AbstractSqlStatement {

	protected String namespaceCode;
	protected String name;

	public DeletePermissionStatement() {
	}

	public DeletePermissionStatement( DeletePermissionChange change ) {
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
	}

	public DeletePermissionStatement( CreatePermissionChange change ) {
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
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
}
