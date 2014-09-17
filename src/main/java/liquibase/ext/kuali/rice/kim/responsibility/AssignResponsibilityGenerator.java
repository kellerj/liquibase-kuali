package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class AssignResponsibilityGenerator extends AbstractSqlGenerator<AssignResponsibilityStatement> {

	@Override
	public ValidationErrors validate(AssignResponsibilityStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("documentType", statement.getDocumentType());
        validationErrors.checkRequiredField("routeNode", statement.getRouteNode());
        validationErrors.checkRequiredField("roleRamespaceCode", statement.getRoleNamespaceCode());
        validationErrors.checkRequiredField("roleName", statement.getRoleName());
        validationErrors.checkRequiredField("actionTypeCode", statement.getActionTypeCode());
        return validationErrors;
	}
}
