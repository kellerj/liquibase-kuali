package liquibase.ext.ucd.workflow;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.ext.ucd.JavaProcess;
import liquibase.statement.SqlStatement;
import liquibase.util.StreamUtil;
import oracle.jdbc.OracleConnection;

@DatabaseChange(name="importWorkflow", description = "Import Workflow", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class ImportWorkflowChange extends AbstractChange {

    private String fileName;
    private String directoryName;
    private Boolean relativeToChangeLogFile = Boolean.TRUE;

    @Override
    public boolean supports(Database database) {
    	return database instanceof OracleDatabase;
    }

    @Override
    public CheckSum generateCheckSum() {
    	if ( fileName != null ) {
    		return generateCheckSumForSingleFile(fileName);
    	} else {
    		CheckSum checksum = CheckSum.compute("");
    		try {
				for ( String fileName : getDirectoryFileNames() ) {
					CheckSum.compute(checksum.toString() + generateCheckSumForSingleFile(fileName).toString());
				}
			} catch (IOException ex) {
				throw new UnexpectedLiquibaseException( "Error obtaining workflow XML files from " + directoryName, ex );
			}
    	}
    	// TODO : get file or files
    	// build checksums from names and contents (name only - no path)
    	// checksum the concat of the checksums
    	return super.generateCheckSum();
    }

    protected CheckSum generateCheckSumForSingleFile( String fileName ) {
        InputStream stream = null;
        try {
            stream = getFileInputStream(fileName);
            return CheckSum.compute(fileName+":"+CheckSum.compute(stream, true).toString());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignore) { }
            }
        }
    }

    protected InputStream getFileInputStream( String fileName ) {
        InputStream stream = null;
        try {
            stream = StreamUtil.openStream(fileName, relativeToChangeLogFile, getChangeSet(), getResourceAccessor());
            if (stream == null) {
                throw new UnexpectedLiquibaseException(fileName + " could not be found");
            }
            stream = new BufferedInputStream(stream);
            return stream;
        } catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    protected List<String> getDirectoryFileNames() throws IOException {
    	if ( directoryName == null ) {
    		return Collections.emptyList();
    	}

        directoryName = directoryName.replace('\\', '/');

        if (!(directoryName.endsWith("/"))) {
            directoryName = directoryName + '/';
        }

        String relativeTo = null;
        if (getRelativeToChangeLogFile()) {
            relativeTo = getChangeSet().getChangeLog().getPhysicalFilePath();
        }

        Set<String> unsortedResources = getResourceAccessor().list(relativeTo, directoryName, true, false, true);
        SortedSet<String> resources = new TreeSet<String>();
        if (unsortedResources != null) {
            for (String resourcePath : unsortedResources) {
                resources.add(resourcePath);
            }
        }

        return new ArrayList<>(resources);
    }

    @Override
	public String getConfirmationMessage() {
    	if ( fileName != null ) {
    		return "Imported Workflow from File: " + fileName;
    	} else {
    		return "Imported Workflow from Directory: " + directoryName;
    	}
    }

    @Override
	public SqlStatement[] generateStatements(Database database) {
    	Properties connectionProperties
    			= ((OracleConnection)((JdbcConnection)database.getConnection())
    					.getUnderlyingConnection()).getProperties();

    	// execute here - pull the connection information from the database object
    	List<String> args = new ArrayList<>();
    	args.add( "-Dworkflow.dir=${workflowPath}/baseline" );
    	args.add( "-Ddatasource.url=" + database.getConnection().getURL() );
    	args.add( "-Ddatasource.username=" + database.getConnection().getConnectionUserName() );
    	args.add( "-Ddatasource.password=" + connectionProperties.getProperty("password") );
    	args.add( "-Drice.server.datasource.url=" + database.getConnection().getURL() );
    	args.add( "-Drice.server.datasource.username=" + database.getConnection().getConnectionUserName() );
    	args.add( "-Drice.server.datasource.password=" + connectionProperties.getProperty("password") );
    	args.add( "-Dbuild.environment=wfimport" );
    	args.add( "clean-all" );
    	args.add( "import-workflow-xml" );

    	try {
			JavaProcess.exec("org.apache.tools.ant.Main", null, null, args );
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return new SqlStatement[0];
    }

	@Override
	public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(super.validate(database));

        if ( fileName == null && directoryName  == null ) {
        	validationErrors.addError("You must specify a fileName or a directoryName for the importWorkflow task.");
        } else if ( fileName != null && directoryName != null ) {
        	validationErrors.addError("You may only specify one of fileName and directoryName on the importWorkflow task.");
        } else if ( fileName != null ) {
            InputStream stream = null;
            try {
                stream = getFileInputStream(fileName);
            } catch ( UnexpectedLiquibaseException ex ) {
            	validationErrors.addError("Unable to access Workflow XML File: " + fileName + " : " + ex.getMessage() );
            	ex.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ignore) { }
                }
            }
        } else if ( directoryName != null ) {
        	try {
				if ( getDirectoryFileNames().isEmpty() ) {
					validationErrors.addError("Directory " + directoryName + " contained no files." );
				}
			} catch (IOException e) {
				validationErrors.addError("Unable to read directory: " + directoryName );
				e.printStackTrace();
			}
        }
        // TODO : file or dir exists

        return validationErrors;
	}

    @DatabaseChangeProperty(description = "Workflow XML File To Load", exampleValue = "workflow/KFS-18130/PA_PurchaseAgreement.xml")
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


    @DatabaseChangeProperty(description = "Workflow XML Files To Load", exampleValue = "workflow/KFS-18130")
	public String getDirectoryName() {
		return directoryName;
	}


	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}


    @DatabaseChangeProperty(description = "File or Direcectory Relative to ChangeLog file?", exampleValue = "true")
	public Boolean getRelativeToChangeLogFile() {
		return relativeToChangeLogFile;
	}


	public void setRelativeToChangeLogFile(Boolean relativeToChangeLogFile) {
		this.relativeToChangeLogFile = relativeToChangeLogFile;
	}
}
