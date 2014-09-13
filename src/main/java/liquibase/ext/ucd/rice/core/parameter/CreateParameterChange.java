package liquibase.ext.ucd.rice.core.parameter;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DeleteDataChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.InsertStatement;

import org.apache.commons.lang.StringUtils;

@DatabaseChange(name="createParameter", description = "Creates a Rice parameter.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreateParameterChange extends ParameterChangeBase {

	protected String value;
	protected String operator;
	protected String parameterTypeCode;
	protected String description;

	@Override
	public String getConfirmationMessage() {
		return "Created Parameter " + namespaceCode + " / " + component + " / " + name + " = " + value;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
//		CreateParameterStatement statement = new CreateParameterStatement(applicationId, namespaceCode, component, parameterTypeCode, value);
//		statement.setDescription(description);
//		statement.setOperatorCode(operatorCode);
//		statement.setParameterTypeCode(parameterTypeCode);

		InsertStatement statement = new InsertStatement(null, null, PARAMETER_TABLE_NAME);
		statement.addColumnValue("APPL_ID", applicationId);
		statement.addColumnValue("NMSPC_CD", namespaceCode);
		statement.addColumnValue("CMPNT_CD", component);
		statement.addColumnValue("PARM_NM", name);
		statement.addColumnValue("VAL", value);
		statement.addColumnValue("PARM_TYP_CD", parameterTypeCode);
		if ( operator != null ) {
			statement.addColumnValue("EVAL_OPRTR_CD", operator.substring(0, 1));
		}
		if ( StringUtils.isNotBlank(description) ) {
			statement.addColumnValue("PARM_DESC_TXT", description);
		}

		return new SqlStatement[] { statement };
	}

	@Override
	protected Change[] createInverses() {
		DeleteDataChange change = new DeleteDataChange();
		change.setTableName(PARAMETER_TABLE_NAME);
		change.setWhere( "APPL_ID = '" + makeQuoteSafe(applicationId) + "' AND NMSPC_CD = '" + makeQuoteSafe(namespaceCode) + "' AND CMPNT_CD = '" + makeQuoteSafe(component) + "' AND PARM_NM = '" + makeQuoteSafe(name) + "'" );

		return new Change[] { change };
	}

	@DatabaseChangeProperty
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@DatabaseChangeProperty
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@DatabaseChangeProperty
	public String getParameterTypeCode() {
		return parameterTypeCode;
	}

	public void setParameterTypeCode(String parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}

	@DatabaseChangeProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
