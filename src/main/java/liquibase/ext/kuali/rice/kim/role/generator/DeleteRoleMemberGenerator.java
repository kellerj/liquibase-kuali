package liquibase.ext.kuali.rice.kim.role.generator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.role.statement.DeleteRoleMemberStatement;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class DeleteRoleMemberGenerator extends AbstractSqlGenerator<DeleteRoleMemberStatement> {

	@Override
	public ValidationErrors validate(DeleteRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		return RoleMemberGeneratorHelper.validate(statement, database, sqlGeneratorChain);
	}
}
