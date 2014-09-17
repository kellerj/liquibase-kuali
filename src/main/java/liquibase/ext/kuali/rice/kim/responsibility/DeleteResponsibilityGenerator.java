package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class DeleteResponsibilityGenerator extends AbstractSqlGenerator<DeleteResponsibilityStatement> {

	@Override
	public ValidationErrors validate(DeleteResponsibilityStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        return validationErrors;
	}
}
