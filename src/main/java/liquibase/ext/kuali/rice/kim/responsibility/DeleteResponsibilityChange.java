package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "deleteResponsibility", description = "Deletes the specified KIM responsibility.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeleteResponsibilityChange extends ResponsibilityChangeBase {

	public DeleteResponsibilityChange() {}

	public DeleteResponsibilityChange( CreateResponsibilityChange createChange ) {
		setNamespaceCode(createChange.getNamespaceCode());
		setName(createChange.getName());
	}


	@Override
	public String getConfirmationMessage() {
		return "DELETED Responsibility " + namespaceCode + " / " + name;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new DeleteResponsibilityStatement(this) };
	}

}
