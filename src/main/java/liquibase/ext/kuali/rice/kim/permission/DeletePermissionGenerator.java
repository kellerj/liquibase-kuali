package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class DeletePermissionGenerator extends AbstractSqlGenerator<DeletePermissionStatement> {

	@Override
	public ValidationErrors validate(DeletePermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        return validationErrors;
	}
}
