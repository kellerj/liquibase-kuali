package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class CreateResponsibilityGenerator extends AbstractSqlGenerator<CreateResponsibilityStatement> {

	@Override
	public ValidationErrors validate(CreateResponsibilityStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        validationErrors.checkRequiredField("documentType", statement.getDocumentType());
        validationErrors.checkRequiredField("routeNode", statement.getRouteNode());
        return validationErrors;
	}
}
