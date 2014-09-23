package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class CreatePermissionGenerator extends AbstractSqlGenerator<CreatePermissionStatement> {

	@Override
	public ValidationErrors validate(CreatePermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        validationErrors.checkRequiredField("permissionTemplateNamespaceCode", statement.getPermissionTemplateNamespaceCode());
        validationErrors.checkRequiredField("permissionTemplateName", statement.getPermissionTemplateName());
        return validationErrors;
	}
}
