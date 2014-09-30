package liquibase.ext.kuali.rice.kim.role.generator;

import java.util.Map;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.kuali.rice.kim.KimSqlGeneratorHelper;
import liquibase.ext.kuali.rice.kim.role.statement.RoleMemberStatementBase;
import liquibase.sqlgenerator.SqlGeneratorChain;

import org.apache.commons.lang.StringUtils;

class RoleMemberGeneratorHelper {

	protected static ValidationErrors validate(RoleMemberStatementBase statement,
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

	protected static String getMemberIdSqlFragment( RoleMemberStatementBase statement ) {
		if ( statement.getMemberTypeCode().equals("P") ) {
			return  "        mbr_id := get_principal_id( '"
					+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getMemberName()) + "' );\n";
		} else if ( statement.getMemberTypeCode().equals("G") ) {
			return  "        mbr_id := get_group_id( '"
					+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getMemberNamespace()) + "', '"
					+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getMemberName()) + "' );\n";
		} else {
			return  "        mbr_id := get_role_id( '"
					+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getMemberNamespace()) + "', '"
					+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getMemberName()) + "' );\n";
		}
	}

	protected static String getRoleMemberSelectorCursor( RoleMemberStatementBase statement ) {
		String sql =
				"CURSOR role_members( MemberId VARCHAR2 ) IS \n"
				+ "SELECT rm.role_mbr_id\n" +
				"    FROM krim_role_mbr_t rm\n" +
				"    INNER JOIN krim_role_t r ON r.ROLE_ID = rm.ROLE_ID\n" +
				"    WHERE r.NMSPC_CD = '"+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleNamespaceCode()) + "'\n" +
				"      AND r.ROLE_NM = '"+ KimSqlGeneratorHelper.makeQuoteSafe(statement.getRoleName()) + "'\n" +
				"      AND rm.mbr_id = MemberId \n" +
				"      AND rm.mbr_typ_cd = '" + statement.getMemberTypeCode() + "'\n ";
		if ( !statement.getAttributes().isEmpty() ) {
			for ( Map.Entry<String,String> attr : statement.getAttributes().entrySet() ) {
				sql += "AND role_mbr_id IN ( SELECT role_mbr_id FROM krim_role_mbr_attr_data_t\n" +
						"        WHERE KIM_ATTR_DEFN_ID IN "
						+ "(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T "
						+ "WHERE NM = '" + KimSqlGeneratorHelper.makeQuoteSafe(attr.getKey()) + "' AND ACTV_IND = 'Y')\n" +
						"          AND ATTR_VAL = '" + KimSqlGeneratorHelper.makeQuoteSafe(attr.getValue()) + "' )\n";
			}
		}
		sql += "   ;\n";
		return sql;
	}
}
