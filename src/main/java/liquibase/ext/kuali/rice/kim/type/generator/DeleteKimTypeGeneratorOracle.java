package liquibase.ext.kuali.rice.kim.type.generator;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.type.statement.DeleteKimTypeStatement;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class DeleteKimTypeGeneratorOracle extends DeleteKimTypeGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

	@Override
	public boolean supports(DeleteKimTypeStatement statement,
			Database database) {
		return database instanceof OracleDatabase;
	}

	@Override
	public Sql[] generateSql(DeleteKimTypeStatement statement,
			Database database, SqlGeneratorChain sqlGeneratorChain) {
		String sql = "DECLARE \n" +
				"   type_id     VARCHAR2(40);\n" +
				"BEGIN\n";
		sql += 	"        SELECT kim_typ_id INTO type_id FROM KRIM_TYP_T WHERE nmspc_cd = '"
				+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getNamespaceCode() ) + "' AND nm = '"
				+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getName() ) + "';\n";
		sql +=
				"        DELETE FROM KRIM_TYP_ATTR_T WHERE kim_typ_id = type_id;\n" +
				"        DELETE FROM KRIM_TYP_T WHERE kim_typ_id = type_id;\n";
		sql +=  "END;";

        return new Sql[]{
                new UnparsedSql(sql)
        };
	}
}
