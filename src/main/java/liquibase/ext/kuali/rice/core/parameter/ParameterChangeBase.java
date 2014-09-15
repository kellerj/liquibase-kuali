package liquibase.ext.kuali.rice.core.parameter;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChangeProperty;

import org.apache.commons.lang.StringUtils;

public abstract class ParameterChangeBase extends AbstractChange {

	protected static final String PARAMETER_TABLE_NAME = "KRCR_PARM_T";

	protected String applicationId;
	protected String namespaceCode;
	protected String component;
	protected String name;

	@DatabaseChangeProperty(description="Application which owns the parameter", exampleValue="KFS")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	protected String makeQuoteSafe( String value ) {
		return StringUtils.replace(value, "'", "''");
	}


	@DatabaseChangeProperty(description="Namespace code for the parameter to be modified", exampleValue="KFS-COA")
	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	@DatabaseChangeProperty(description="Name of the parameter to be modified", exampleValue="ACCOUNT_NUMBER_VALID_PATTERN")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DatabaseChangeProperty(description="Component of the parameter to be modified", exampleValue="Account")
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
