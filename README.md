Kuali Liquibase Extensions
========================

This project contains (will contain) a set of Liquibase extensions which will be used to provide Kuali environment-specific extensions to the Liquibase tool.

Using These Extensions
----------------------

To use these extensions, you must declare the `kuali` namespace in the XML Header.

This requires adding:

	xmlns:kuali="http://www.liquibase.org/xml/ns/dbchangelog-kuali"

to the namespace definitions and:

	http://www.liquibase.org/xml/ns/dbchangelog-kuali ../../dbchangelog-kuali.xsd
	
to the `xsi:schemaLocation` attribute.

As of this writing, your XML header in our Liquibase files should look like:

```
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ora="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:kuali="http://www.liquibase.org/xml/ns/dbchangelog-kuali"
    xsi:schemaLocation="
    		http://www.liquibase.org/xml/ns/dbchangelog     ../../dbchangelog-3.2.xsd
			http://www.liquibase.org/xml/ns/dbchangelog-ext ../../dbchangelog-ext.xsd
			http://www.liquibase.org/xml/ns/dbchangelog-kuali ../../dbchangelog-kuali.xsd
    ">
```

Implemented Extensions
======================

## Summary

| Tag Name                  | Rollback? | Purpose |
| ------------------------- | --------- | ------- |
| `<kuali:createRole>`      | Yes       | Creates a role of the given type, auto-generating the ID if needed. |
| `<kuali:createParameter>` | Yes       | Create a parameter in the KRCR_PARM_T table.
| `<kuali:importWorkflow>`  | **No**    | Imports the workflow XML files in a particular file or directory of files.  **Presently requires a checkout of KFS for use of the `import-workflow-xml` Ant target.** |

## KNS Data

### Create Parameter

  	<kuali:createParameter applicationId="KFS" 
						   namespaceCode="KFS-COA"
						   component="Organization"
						   name="MSO_ALLOWED_ORG_TYPE_CODES"
						   value="D;N"
						   parameterTypeCode="VALID" (or CONFG/HELP)
						   operator="ALLOW" (or DENY)
						   description="Organization Type Codes for which an MSO may be assigned. (And for which the fields will be visible.)" />

## KEW Data

### Workflow XML Import

You can now import workflow XML via this tool.  The syntax is as follows:

	<kuali:importWorkflow directoryName="workflow/KFS-18130" />
	
or

	<kuali:importWorkflow fileName="workflow/KFS-18130.xml" />

The given paths are relative to the directory of the changelog file.  So, if the changelog file is `rice/rice/KFS-18130.xml`, the first example above would point to `rice/rice/workflow/KFS-18130/`.

## KIM Data

### Create Role

	<kuali:createRole applicationId="KFS" 
					  roleNamespaceCode="KFS-SYS"
					  roleName="" 
					  roleId="" (optional)
					  roleTypeNamespace="KUALI"
					  roleTypeName="Default"
  					  roleDescription="" />


Planned Extensions
==================

* Modification of Roles
* Inactivation/Removal of Roles
* Addition/Inactivation of role members
* Create/Modify Permissions
* Create/Modify Responsibilities
* Create KIM Types / Attributes
* Update/Remove Parameters
* Permission-Role Assignment
* Responsibility-Role Assignment
* Exception Responsibility Creation
* Multi-Database Support (initial work is Oracle-specific)