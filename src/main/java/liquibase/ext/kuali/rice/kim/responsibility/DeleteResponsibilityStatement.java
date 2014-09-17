package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.statement.AbstractSqlStatement;

public class DeleteResponsibilityStatement extends AbstractSqlStatement {

	protected String namespaceCode;
	protected String name;

	public DeleteResponsibilityStatement() {
	}

	public DeleteResponsibilityStatement( DeleteResponsibilityChange change ) {
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
	}

	public DeleteResponsibilityStatement( CreateResponsibilityChange change ) {
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
