package liquibase.ext.kuali.rice.kim.role.generator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.role.statement.AddRoleMemberStatement;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class AddRoleMemberGenerator extends AbstractSqlGenerator<AddRoleMemberStatement> {

	@Override
	public ValidationErrors validate(AddRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		return RoleMemberGeneratorHelper.validate(statement, database, sqlGeneratorChain);
	}
}
