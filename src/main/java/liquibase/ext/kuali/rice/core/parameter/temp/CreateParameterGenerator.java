package liquibase.ext.kuali.rice.core.parameter.temp;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class CreateParameterGenerator extends AbstractSqlGenerator<CreateParameterStatement> {

	@Override
	public boolean supports(CreateParameterStatement statement,
			Database database) {
		return true;
	}

	@Override
	public ValidationErrors validate(CreateParameterStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("applicationId", statement.getApplicationId());
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("component", statement.getComponent());
        validationErrors.checkRequiredField("name", statement.getName());
        validationErrors.checkRequiredField("value", statement.getValue());
        return validationErrors;
	}

	@Override
	public Sql[] generateSql(CreateParameterStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		// TODO Auto-generated method stub
		return null;
	}


}
