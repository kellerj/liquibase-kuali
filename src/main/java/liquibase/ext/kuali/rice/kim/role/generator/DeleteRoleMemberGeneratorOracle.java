package liquibase.ext.kuali.rice.kim.role.generator;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.role.statement.DeleteRoleMemberStatement;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeleteRoleMemberGeneratorOracle extends DeleteRoleMemberGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeleteRoleMemberStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeleteRoleMemberStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"   mbr_id      VARCHAR2(40);\n";
		// set up a cursor for the role member IDs to delete
		sql += RoleMemberGeneratorHelper.getRoleMemberSelectorCursor(statement);
		if ( statement.getMemberTypeCode().equals("P") ) {
			sql += KimSqlGeneratorHelper.getPrincipalIdFunctionSql();
		} else if ( statement.getMemberTypeCode().equals("G") ) {
			sql += KimSqlGeneratorHelper.getGroupIdFunctionSql();
		} else {
			sql += KimSqlGeneratorHelper.getRoleIdFunctionSql();
		}
		sql +=	"BEGIN\n"
				+ RoleMemberGeneratorHelper.getMemberIdSqlFragment(statement)
				+ "   FOR rec IN role_members( mbr_id ) LOOP\n "
				+ "      DELETE FROM krim_role_mbr_attr_data_t WHERE role_mbr_id = rec.role_mbr_id;\n"
				+ "      DELETE FROM krim_role_mbr_t WHERE role_mbr_id = rec.role_mbr_id;\n"
				+ "   END LOOP;\n";
		sql +=  "END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
