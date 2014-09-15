package liquibase.ext.kuali.rice.core.parameter.temp;


public class CreateParameterStatement extends AbstractParameterChangeStatement {

	protected String value;
	protected String operatorCode;
	protected String parameterTypeCode;
	protected String description;

	public CreateParameterStatement(String applicationId, String namespaceCode,
			String component, String name, String value ) {
		super(applicationId, namespaceCode, component, name);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getParameterTypeCode() {
		return parameterTypeCode;
	}

	public void setParameterTypeCode(String parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
