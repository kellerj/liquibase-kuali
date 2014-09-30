package liquibase.ext.kuali.rice.kim.role.statement;

import java.util.Date;

import liquibase.ext.kuali.rice.kim.role.change.AddRoleMemberChange;

public class AddRoleMemberStatement extends RoleMemberStatementBase {

	public AddRoleMemberStatement() {}
	
	public AddRoleMemberStatement( AddRoleMemberChange change ) {
		super(change);
		activeDate = change.getActiveDate();
		inactiveDate = change.getInactiveDate();
	}
	
	protected Date activeDate;
	protected Date inactiveDate;

	public Date getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	public Date getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

}
