package liquibase.ext.ucd.updatedata;

public class InsertOrUpdateStatement extends liquibase.statement.core.InsertOrUpdateStatement {
    private Boolean updateOnly = Boolean.FALSE;

	public InsertOrUpdateStatement(String catalogName, String schemaName,
			String tableName, String primaryKey, boolean updateOnly) {
		super(catalogName, schemaName, tableName, primaryKey);
		this.updateOnly = updateOnly;
	}

    public Boolean getUpdateOnly() {
    	if ( updateOnly == null ) {
    		return false;
    	}
		return updateOnly;
	}

	public void setUpdateOnly(Boolean updateOnly) {
		this.updateOnly = updateOnly;
	}

}
