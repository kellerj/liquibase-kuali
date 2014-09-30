package liquibase.ext.kuali.rice.kim.role.change;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import liquibase.change.DatabaseChangeProperty;
import liquibase.ext.kuali.rice.kim.role.RoleChangeBase;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;

public abstract class RoleMemberChangeBase extends RoleChangeBase {

	protected String memberType;
	protected String memberNamespace;
	protected String memberName;
	
	protected Map<String,String> attributes = new HashMap<>();
	
	public RoleMemberChangeBase() {}
	
	public RoleMemberChangeBase( RoleMemberChangeBase change ) {
		roleName = change.roleName;
		roleNamespaceCode = change.roleNamespaceCode;
		memberType = change.memberType;
		memberNamespace = change.memberNamespace;
		memberName = change.memberName;
		attributes.putAll(change.attributes);
	}
	
	
	@Override
	protected void customLoadLogic(ParsedNode parsedNode,
			ResourceAccessor resourceAccessor) throws ParsedNodeException {
        List<ParsedNode> attributeNodes = parsedNode.getChildren(null, "attribute");
		for ( ParsedNode node : attributeNodes ) {
			addAttribute( 
					node.getChildValue(null, "name", String.class),
					node.getChildValue(null, "value", String.class) );
		}
	}
	
	protected void addAttribute( String name, String value ) {
		attributes.put(name, value);
	}
	
	@DatabaseChangeProperty
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@DatabaseChangeProperty
	public String getMemberNamespace() {
		return memberNamespace;
	}
	public void setMemberNamespace(String memberNamespace) {
		this.memberNamespace = memberNamespace;
	}

	@DatabaseChangeProperty
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Map<String, String> getAttributes() {
		if ( attributes == null ) {
			attributes = new HashMap<>();
		}
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
