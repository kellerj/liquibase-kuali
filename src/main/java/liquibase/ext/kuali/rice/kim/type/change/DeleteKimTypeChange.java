package liquibase.ext.kuali.rice.kim.type.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.KimChangeBase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.type.statement.DeleteKimTypeStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DeleteStatement;

import org.apache.commons.lang.StringUtils;

@DatabaseChange(name = "deleteKimType", description = "", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeleteKimTypeChange extends KimChangeBase {

	protected String namespaceCode;
	protected String name;
	protected String typeId;

	public DeleteKimTypeChange() {}

	public DeleteKimTypeChange( CreateKimTypeChange change ) {
		setApplicationId(change.getApplicationId());
		namespaceCode = change.getNamespaceCode();
		name = change.getName();
		typeId = change.getTypeId();
	}

	@Override
	public String getConfirmationMessage() {
		return "DELETING KIM Type: " + namespaceCode + " / " + name;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		if ( StringUtils.isNotBlank(typeId) ) {
			DeleteStatement deleteAttributesStatement = new DeleteStatement(null, null, "KRIM_TYP_ATTR_T");
			deleteAttributesStatement.setWhere("KIM_TYP_ID = '" + KimSqlGeneratorHelper.makeQuoteSafe(typeId) + "'");
			DeleteStatement deleteTypeStatement = new DeleteStatement(null,null,"KRIM_TYP_T");
			deleteTypeStatement.setWhere("KIM_TYP_ID = '" + KimSqlGeneratorHelper.makeQuoteSafe(typeId) + "'");

			return new SqlStatement[] { deleteAttributesStatement, deleteTypeStatement };
		} else {
			return new SqlStatement[] { new DeleteKimTypeStatement( this ) };
		}
	}

	@DatabaseChangeProperty
	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	@DatabaseChangeProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DatabaseChangeProperty
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
