package liquibase.ext.kuali.rice.kim.role.statement;

import liquibase.ext.kuali.rice.kim.role.change.RoleMemberChangeBase;


public class DeleteRoleMemberStatement extends RoleMemberStatementBase {

	public DeleteRoleMemberStatement() {}
	
	public DeleteRoleMemberStatement( RoleMemberChangeBase change ) {
		super(change);
	}
}
