package liquibase.ext.kuali.rice.kim.permission;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class AssignPermissionGenerator extends AbstractSqlGenerator<AssignPermissionStatement> {

	@Override
	public ValidationErrors validate(AssignPermissionStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("roleNamespaceCode", statement.getRoleNamespaceCode());
        validationErrors.checkRequiredField("roleName", statement.getRoleName());
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        return validationErrors;
	}
}
