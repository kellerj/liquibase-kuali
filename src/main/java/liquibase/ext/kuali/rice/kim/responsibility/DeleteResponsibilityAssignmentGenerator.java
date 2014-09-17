package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class DeleteResponsibilityAssignmentGenerator extends AbstractSqlGenerator<DeleteResponsibilityAssignmentStatement> {

	@Override
	public ValidationErrors validate(DeleteResponsibilityAssignmentStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("roleNamespaceCode", statement.getRoleNamespaceCode());
        validationErrors.checkRequiredField("roleName", statement.getRoleName());
        validationErrors.checkRequiredField("documentType", statement.getDocumentType());
        validationErrors.checkRequiredField("routeNode", statement.getRouteNode());
        return validationErrors;
	}
}
