package liquibase.ext.kuali.rice.kim.role.statement;

import java.util.Date;

import liquibase.ext.kuali.rice.kim.role.change.InactivateRoleMemberChange;

public class InactivateRoleMemberStatement extends RoleMemberStatementBase {

	protected Date inactiveDate;

	public InactivateRoleMemberStatement() {}

	public InactivateRoleMemberStatement( InactivateRoleMemberChange change ) {
		super(change);
		inactiveDate = change.getInactiveDate();
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

}
