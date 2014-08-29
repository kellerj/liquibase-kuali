package liquibase.ext.ucd.updatedata;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.statement.core.InsertStatement;


@DatabaseChange(name="loadUpdateData",
description = "Loads or updates data from a CSV file into an existing table. Differs from loadData by issuing a SQL batch that checks for the existence of a record. If found, the record is UPDATEd, else the record is INSERTed. Also, generates DELETE statements for a rollback.\n" +
        "\n" +
        "A value of NULL in a cell will be converted to a database NULL rather than the string 'NULL'",
priority = ChangeMetaData.PRIORITY_DEFAULT, appliesTo = "table")
public class LoadUpdateDataChange extends liquibase.change.core.LoadUpdateDataChange {
    private Boolean updateOnly = Boolean.FALSE;

    @DatabaseChangeProperty(description = "Whether records with no matching database record should be ignored" )
    public Boolean getUpdateOnly() {
    	if ( updateOnly == null ) {
    		return false;
    	}
		return updateOnly;
	}

	public void setUpdateOnly(Boolean updateOnly) {
		this.updateOnly = updateOnly;
	}

	@Override
	protected InsertStatement createStatement(String catalogName,
			String schemaName, String tableName) {
		return new InsertOrUpdateStatement(catalogName, schemaName, tableName, getPrimaryKey(), getUpdateOnly());
	}

}
