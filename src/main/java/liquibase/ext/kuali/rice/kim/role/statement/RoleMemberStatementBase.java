package liquibase.ext.kuali.rice.kim.role.statement;

import java.util.HashMap;
import java.util.Map;

import liquibase.ext.kuali.rice.kim.role.change.RoleMemberChangeBase;

public abstract class RoleMemberStatementBase extends RoleStatementBase {

	protected String memberTypeCode;
	protected String memberNamespace;
	protected String memberName;
	
	protected Map<String,String> attributes = new HashMap<>();
	
	public RoleMemberStatementBase() {}
	
	public RoleMemberStatementBase( RoleMemberChangeBase change ) {
		roleName = change.getRoleName();
		roleNamespaceCode = change.getRoleNamespaceCode();
		memberTypeCode = "P";
		if ( change.getMemberType() != null ) {
			switch ( change.getMemberType() ) {
				case "PERSON" :
					memberTypeCode = "P";
					break;
				case "ROLE" :
					memberTypeCode = "R";
					break;
				case "GROUP" :
					memberTypeCode = "G";
					break;
			}
		}
		memberNamespace = change.getMemberNamespace();
		memberName = change.getMemberName();
		attributes.putAll(change.getAttributes());
	}
	
	
	public String getMemberTypeCode() {
		return memberTypeCode;
	}
	public void setMemberTypeCode(String memberTypeCode) {
		this.memberTypeCode = memberTypeCode;
	}

	public String getMemberNamespace() {
		return memberNamespace;
	}
	public void setMemberNamespace(String memberNamespace) {
		this.memberNamespace = memberNamespace;
	}

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
