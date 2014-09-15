package liquibase.ext.kuali.rice.core.parameter.temp;

import liquibase.statement.AbstractSqlStatement;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractParameterChangeStatement extends AbstractSqlStatement {

	protected String applicationId;
	protected String namespaceCode;
	protected String component;
	protected String name;

	public AbstractParameterChangeStatement(String applicationId,
			String namespaceCode, String component, String name) {
		super();
		this.applicationId = applicationId;
		this.namespaceCode = namespaceCode;
		this.component = component;
		this.name = name;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	protected String makeQuoteSafe( String value ) {
		return StringUtils.replace(value, "'", "''");
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

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
