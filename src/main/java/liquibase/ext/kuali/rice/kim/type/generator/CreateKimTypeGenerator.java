package liquibase.ext.kuali.rice.kim.type.generator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.type.statement.CreateKimTypeStatement;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

import org.apache.commons.lang.StringUtils;

public abstract class CreateKimTypeGenerator extends AbstractSqlGenerator<CreateKimTypeStatement> {

	@Override
	public ValidationErrors validate(CreateKimTypeStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("namespaceCode", statement.getNamespaceCode());
        validationErrors.checkRequiredField("name", statement.getName());
        for ( String attributeName : statement.getAttributeNames() ) {
        	if ( StringUtils.isBlank(attributeName) ) {
        		validationErrors.addError("kimAttribute.name must not be blank or missing");
        	}
        }
        return validationErrors;
	}
}
