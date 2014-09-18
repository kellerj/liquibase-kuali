package liquibase.ext.kuali.rice.kim.type.generator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.type.statement.DeleteKimTypeStatement;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class DeleteKimTypeGenerator extends AbstractSqlGenerator<DeleteKimTypeStatement> {

	@Override
	public ValidationErrors validate(DeleteKimTypeStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        return validationErrors;
	}
}
