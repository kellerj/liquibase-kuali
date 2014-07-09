package liquibase.ext.ucd.workflow;

import liquibase.statement.AbstractSqlStatement;

public class ImportWorkflowStatement extends AbstractSqlStatement {
    private String schemaName;
    private String tableName;
    private String clusterName;
    private Boolean purgeMaterializedViewLog;
    private Boolean reuseStorage;

    public ImportWorkflowStatement(String schemaName, String tableName, String clusterName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.clusterName = clusterName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public Boolean purgeMaterializedViewLog() {
        return purgeMaterializedViewLog;
    }

    public ImportWorkflowStatement setPurgeMaterializedViewLog(Boolean purgeMaterializedViewLog) {
        this.purgeMaterializedViewLog = purgeMaterializedViewLog;
        return this;
    }

    public Boolean reuseStorage() {
        return reuseStorage;
    }

    public ImportWorkflowStatement setReuseStorage(Boolean reuseStorage) {
        this.reuseStorage = reuseStorage;
        return this;
    }
}
