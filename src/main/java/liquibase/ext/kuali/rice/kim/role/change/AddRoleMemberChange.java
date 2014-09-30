package liquibase.ext.kuali.rice.kim.role.change;

import java.util.Date;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.statement.AddRoleMemberStatement;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="addRoleMember", description = "", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class AddRoleMemberChange extends RoleMemberChangeBase {

	protected Date activeDate;
	protected Date inactiveDate;
	
	@Override
	public String getConfirmationMessage() {
		return "Adding " + memberType + " " + memberNamespace + " " + memberName + " to role " + roleNamespaceCode + " / " + roleName; 
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new AddRoleMemberStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		return new Change[] { new DeleteRoleMemberChange(this) };
	}
	
	@DatabaseChangeProperty
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@DatabaseChangeProperty
	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

}
