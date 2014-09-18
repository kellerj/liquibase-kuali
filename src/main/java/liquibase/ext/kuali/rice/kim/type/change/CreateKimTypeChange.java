package liquibase.ext.kuali.rice.kim.type.change;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.ext.kuali.rice.kim.KimChangeBase;
import liquibase.ext.kuali.rice.kim.type.statement.CreateKimTypeStatement;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "createKimType", description = "", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreateKimTypeChange extends KimChangeBase {

	protected String namespaceCode;
	protected String name;
	protected String serviceName;
	protected String typeId;
	protected List<String> attributeNames = new ArrayList<>();

	@Override
	public String getConfirmationMessage() {
		return "Created KIM Type: " + namespaceCode + " / " + name;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) {
		return new SqlStatement[] { new CreateKimTypeStatement(this) };
	}

	@Override
	protected Change[] createInverses() {
		return new Change[] { new DeleteKimTypeChange(this) };
	}

	@Override
	protected void customLoadLogic(ParsedNode parsedNode,
			ResourceAccessor resourceAccessor) throws ParsedNodeException {
        List<ParsedNode> attributeNodes = parsedNode.getChildren(null, "kimAttribute");
		for ( ParsedNode node : attributeNodes ) {
			addAttribute( node.getChildValue(null, "name", String.class) );
		}
	}

	@DatabaseChangeProperty
	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	@DatabaseChangeProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DatabaseChangeProperty
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	public void addAttribute( String attributeName ) {
		attributeNames.add(attributeName);
	}

	@DatabaseChangeProperty
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
