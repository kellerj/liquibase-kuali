package liquibase.ext.kuali.rice.kim.type.statement;

import liquibase.ext.kuali.rice.kim.type.change.DeleteKimTypeChange;
import liquibase.statement.AbstractSqlStatement;

public class DeleteKimTypeStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String name;
	protected String typeId;

	public DeleteKimTypeStatement() {}

	public DeleteKimTypeStatement( DeleteKimTypeChange change ) {
		applicationId = change.getApplicationId();
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
		typeId = change.getTypeId();
	}

	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
