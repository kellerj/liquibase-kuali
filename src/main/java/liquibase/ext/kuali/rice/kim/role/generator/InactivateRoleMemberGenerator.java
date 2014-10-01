package liquibase.ext.kuali.rice.kim.role.generator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.role.statement.InactivateRoleMemberStatement;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public abstract class InactivateRoleMemberGenerator extends AbstractSqlGenerator<InactivateRoleMemberStatement> {

	@Override
	public ValidationErrors validate(InactivateRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		return RoleMemberGeneratorHelper.validate(statement, database, sqlGeneratorChain);
	}
}
