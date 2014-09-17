package liquibase.ext.kuali.rice.kim.responsibility;

import liquibase.change.DatabaseChangeProperty;
import liquibase.ext.kuali.rice.kim.KimChangeBase;

public abstract class ResponsibilityChangeBase extends KimChangeBase {

	protected String namespaceCode;
	protected String name;

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

}
