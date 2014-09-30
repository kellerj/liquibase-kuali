package liquibase.ext.kuali.rice.kim.role.generator;

import java.util.Map;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.role.statement.RoleMemberStatementBase;
import liquibase.sqlgenerator.SqlGeneratorChain;

import org.apache.commons.lang.StringUtils;

public class RoleMemberGeneratorHelper {

	public static ValidationErrors validate(RoleMemberStatementBase statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("roleNamespaceCode", statement.getRoleNamespaceCode());
        validationErrors.checkRequiredField("roleName", statement.getRoleName());
        validationErrors.checkRequiredField("memberTypeCode", statement.getMemberTypeCode());
        validationErrors.checkRequiredField("memberName", statement.getMemberName());
        if ( !statement.getMemberTypeCode().equals("P") && StringUtils.isBlank(statement.getMemberNamespace() ) ) {
    		validationErrors.addError("memberNamespace must not be blank or missing when altering a Group or Role member");
        }
        for ( Map.Entry<String,String> attributeName : statement.getAttributes().entrySet() ) {
        	if ( StringUtils.isBlank(attributeName.getKey()) ) {
        		validationErrors.addError("attribute.name must not be blank or missing");
        	}
        }
        return validationErrors;
	}
}
