package liquibase.ext.ucd.workflow;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class ImportWorkflowGenerator extends AbstractSqlGenerator<ImportWorkflowStatement> {

    @Override
	public boolean supports(ImportWorkflowStatement importWorkflowStatement, Database database) {
        return true;
    }

    @Override
	public ValidationErrors validate(ImportWorkflowStatement importWorkflowStatement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors = new ValidationErrors();

        return errors;
    }

    @Override
	public Sql[] generateSql(ImportWorkflowStatement importWorkflowStatement,
                             Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[0];
    }
}