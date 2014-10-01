package liquibase.ext.kuali.rice.kim.role.change;

import java.util.Date;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.statement.InactivateRoleMemberStatement;
import liquibase.statement.SqlStatement;

import org.apache.commons.lang.StringUtils;

@DatabaseChange(name="inactivateRoleMember", description = "", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class InactivateRoleMemberChange extends RoleMemberChangeBase {

	protected Date inactiveDate;

	@Override
	public String getConfirmationMessage() {
		return "Inactivating " + memberType + " " + StringUtils.trimToEmpty(memberNamespace) + " " + memberName + " as of " + (inactiveDate==null?"NOW":inactiveDate) + " from role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new InactivateRoleMemberStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		return null; // we can't roll back, we don't have the original value if there was one
	}

	@DatabaseChangeProperty
	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

}
