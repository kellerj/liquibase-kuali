package liquibase.ext.kuali.rice.kim.type.statement;

import java.util.ArrayList;
import java.util.List;

import liquibase.ext.kuali.rice.kim.type.change.CreateKimTypeChange;
import liquibase.statement.AbstractSqlStatement;

public class CreateKimTypeStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String name;
	protected String serviceName;
	protected String typeId;
	protected List<String> attributeNames = new ArrayList<>();

	public CreateKimTypeStatement() {}

	public CreateKimTypeStatement( CreateKimTypeChange change ) {
		applicationId = change.getApplicationId();
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
		typeId = change.getTypeId();
		serviceName = change.getServiceName();
		attributeNames = change.getAttributeNames();
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public List<String> getAttributeNames() {
		return attributeNames;
	}
	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

}
