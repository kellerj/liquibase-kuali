package liquibase.ext.kuali.workflow;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.ext.kuali.JavaProcess;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.util.StreamUtil;

import org.apache.commons.io.FileUtils;

@DatabaseChange(name="importWorkflow", description = "Import Workflow XML", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class ImportWorkflowChange extends AbstractChange {

    private String fileName;
    private String directoryName;

    @Override
    public boolean supports(Database database) {
    	return database instanceof OracleDatabase;
    }

    @Override
    public CheckSum generateCheckSum() {
    	if ( fileName != null ) {
    		return generateCheckSumForSingleFile(getRelativeFilePath(fileName));
    	} else {
    		try {
    			StringBuilder checkSumString = new StringBuilder();
				for ( String fileName : getDirectoryFileNames() ) {
					checkSumString.append(generateCheckSumForSingleFile(fileName).toString());
				}
				// now, checksum the filename/checksum combos
				return CheckSum.compute(checkSumString.toString());
			} catch (IOException ex) {
				throw new UnexpectedLiquibaseException( "Error obtaining workflow XML files from " + directoryName, ex );
			}
    	}
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
            stream = StreamUtil.openStream(fileName, false, getChangeSet(), new FileSystemResourceAccessor( System.getProperty("user.dir") ));
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

        File baseDir = new File( System.getProperty("user.dir") );
        File changeLogDir = new File( baseDir, getChangeSet().getChangeLog().getPhysicalFilePath() ).getParentFile();
        File workflowDir = new File( changeLogDir, directoryName );
        String baseDirUnix = baseDir.getAbsolutePath().replace('\\','/');

        File[] unsortedResources = workflowDir.listFiles( new FileFilter() {
        	@Override
        	public boolean accept(File pathname) {
        		return pathname.isFile() && pathname.getName().endsWith(".xml");
        	}
        });
        SortedSet<String> resources = new TreeSet<String>();
        if (unsortedResources != null) {
            for (File resourcePath : unsortedResources) {
            	String unixFilePath = resourcePath.getAbsolutePath().replace('\\','/');
                resources.add(unixFilePath.replaceFirst(baseDirUnix+"/", ""));
            }
        }
        //System.out.println( "Returning: " + resources );
        return new ArrayList<>(resources);
    }

    protected String getRelativeFilePath( String fileName ) {
        File baseDir = new File( System.getProperty("user.dir") );
        File changeLogDir = new File( baseDir, getChangeSet().getChangeLog().getPhysicalFilePath() ).getParentFile();
        File workflowFile = new File( changeLogDir, fileName );

        return workflowFile.getAbsolutePath().replace('\\','/').replaceFirst(baseDir.getAbsolutePath()+"/", "");
    }

    @Override
	public String getConfirmationMessage() {
    	if ( fileName != null ) {
    		return "Imported Workflow from File: " + fileName;
    	} else {
    		return "Imported Workflow from Directory: " + directoryName;
    	}
    }

    /**
     * Stopping certain validation which was running the workflow at the wrong time
     */
    @Override
    public boolean generateStatementsVolatile(Database database) {
    	return true;
    }

    @Override
	public SqlStatement[] generateStatements(Database database) {
		if ( ExecutorService.getInstance().getExecutor(database).updatesDatabase() ) {
			try {
				Path tempDirectory = Files.createTempDirectory("liquibase-workflow");
				System.out.println( "Using Temp Directory for Workflow: " + tempDirectory );
				if ( directoryName != null ) {
					FileUtils.copyDirectory(new File( getRelativeFilePath(directoryName) ), tempDirectory.toFile().getAbsoluteFile() );
				} else {
					FileUtils.copyFileToDirectory( new File( getRelativeFilePath(fileName) ), tempDirectory.toFile().getAbsoluteFile() );
				}

		    	// execute here - pull the connection information from the database object
		    	List<String> args = new ArrayList<>();
		    	args.add( "-Dworkflow.dir=" + tempDirectory.toString().replace('\\', '/') );
		    	args.add( "-Ddatasource.url=" + database.getConnection().getURL() );
		    	args.add( "-Ddatasource.username=" + database.getConnection().getConnectionUserName() );
		    	args.add( "-Ddatasource.password=" + getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.database.password" ) );
		    	args.add( "-Drice.server.datasource.url=" + database.getConnection().getURL() );
		    	args.add( "-Drice.server.datasource.username=" + database.getConnection().getConnectionUserName() );
		    	args.add( "-Drice.server.datasource.password=" + getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.database.password") );
		    	args.add( "-Djava.awt.headless=true" );
		    	args.add( "-Dbuild.environment=wfimport" );
		    	// just to speed things up if we have multiple workflow to run
		    	Object value = getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.clean-already-run");
		    	if ( value == null ) {
		    		args.add( "clean-all" );
		    		getChangeSet().getChangeLog().getChangeLogParameters().set("import.workflow.clean-already-run", Boolean.TRUE);
    			}
		    	args.add( "import-workflow-xml" );

				JavaProcess.exec("org.apache.tools.ant.Main",
						getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.kfs.project.location").toString(),
						getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.classpath").toString(),
						Arrays.asList(getChangeSet().getChangeLog().getChangeLogParameters().getValue("import.workflow.additional.jvm.args").toString().split(",")), 
						args );
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

	    	return new SqlStatement[0];
		} else {
			StringBuilder sb = new StringBuilder();
			if ( directoryName != null ) {
				sb.append( "-- Will import workflow XML files in the ").append( getRelativeFilePath(directoryName) ).append( " directory:\n" );
				try {
					for ( String fileName : getDirectoryFileNames() ) {
						sb.append( "-- ---> " ).append( fileName ).append( "\n" );
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				sb.append( "Will import workflow XML file: " ).append( getRelativeFilePath(fileName) ).append( "\n" );
			}

			return new SqlStatement[] { new RawSqlStatement( sb.toString() ) };
		}
    }

	@Override
	public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(super.validate(database));

        if ( fileName == null && directoryName == null ) {
        	validationErrors.addError("You must specify a fileName or a directoryName for the importWorkflow task.");
        } else if ( fileName != null && directoryName != null ) {
        	validationErrors.addError("You may only specify one of fileName and directoryName on the importWorkflow task.");
        } else if ( fileName != null ) {
            InputStream stream = null;
            try {
                stream = getFileInputStream( getRelativeFilePath(fileName) );
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
}
