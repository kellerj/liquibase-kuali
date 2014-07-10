UCD Liquibase Extensions
========================

This project contains (will contain) a set of Liquibase extensions which will be used to provide UCD environment-specific extensions to the Liquibase tool.

Using These Extensions
----------------------

To use these extensions, you must declare the `ucd` namespace in the XML Header.

This requires adding:

	xmlns:ucd="http://www.liquibase.org/xml/ns/dbchangelog-ucd"

to the namespace definitions and:

	http://www.liquibase.org/xml/ns/dbchangelog-ucd ../../dbchangelog-ucd.xsd
	
to the `xsi:schemaLocation` attribute.

As of this writing, your XML header in our Liquibase files should look like:

```
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ora="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:ucd="http://www.liquibase.org/xml/ns/dbchangelog-ucd"
    xsi:schemaLocation="
    		http://www.liquibase.org/xml/ns/dbchangelog     ../../dbchangelog-3.2.xsd
			http://www.liquibase.org/xml/ns/dbchangelog-ext ../../dbchangelog-ext.xsd
			http://www.liquibase.org/xml/ns/dbchangelog-ucd ../../dbchangelog-ucd.xsd
    ">
```

Implemented Extensions
----------------------

### Workflow XML Import

You can now import workflow XML via this tool.  The syntax is as follows:

	<ucd:importWorkflow directoryName="workflow/KFS-18130" relativeToChangeLogFile="true" />
	
or

	<ucd:importWorkflow fileName="workflow/KFS-18130.xml" relativeToChangeLogFile="true" />

These would always be placed in the `rice/rice` scripts, since that connection information is what is needed to ingest workflow XML.

Planned Extensions
------------------

* Workflow XML Import improvements
* Custom Standard Table Grants (KFS_SELECT_ROLE, KFS_USER_ROLE, etc...)