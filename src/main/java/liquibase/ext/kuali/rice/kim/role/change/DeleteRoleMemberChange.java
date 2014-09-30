package liquibase.ext.kuali.rice.kim.role.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.role.statement.DeleteRoleMemberStatement;
import liquibase.statement.SqlStatement;

import org.apache.commons.lang.StringUtils;

@DatabaseChange(name="deleteRoleMember", description = "", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DeleteRoleMemberChange extends RoleMemberChangeBase {

	public DeleteRoleMemberChange() {}

	public DeleteRoleMemberChange(RoleMemberChangeBase change) {
		super(change);
	}


	@Override
	public String getConfirmationMessage() {
		return "Deleting " + memberType + " " + StringUtils.trimToEmpty(memberNamespace) + " " + memberName + " from role " + roleNamespaceCode + " / " + roleName;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new DeleteRoleMemberStatement(this) };
	}
}
