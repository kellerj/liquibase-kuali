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

| Tag Name                               | Rollback? | Purpose |
| -------------------------------------- | --------- | ------- |
| `kuali:createRole`                     | Yes       | Creates a role of the given type, auto-generating the ID if needed. |
| `kuali:createParameter`                | Yes       | Create a parameter in the KRCR_PARM_T table.
| `kuali:importWorkflow`                 | **No**    | Imports the workflow XML files in a particular file or directory of files.  **Presently requires a checkout of KFS for use of the `import-workflow-xml` Ant target.** |
| `kuali:createResponsibility`           | Yes       | |
| `kuali:assignResponsibility`           | Yes       | | 
| `kuali:createPermission`               | Yes       | |
| `kuali:assignPermission`               | Yes       | | 
| `kuali:createKimType`                  | Yes       | |
| `kuali:addRoleMember`                  | Yes       | |
| `kuali:deleteResponsibility`           | **No**    | |
| `kuali:deleteResponsibilityAssignment` | **No**    | |
| `kuali:deletePermission`               | **No**    | |
| `kuali:deletePermissionAssignment`     | **No**    | |
| `kuali:deleteKimType`                  | **No**    | |
| `kuali:deleteRoleMember`               | **No**    | |
| `kuali:inactivateRoleMember`           | **No**    | |

Example Usages
==============

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

### General

### Create KIM Type

	<kuali:createKimType namespaceCode="KFS-SYS" 
	                     name="Organization-Based Derived Role" 
	                     typeId="ORG_DERIVED" <!-- (optional) -->
			               serviceName="{http://kfs.kuali.org/kfs/v5_0}organizationBasedDerivedRoleTypeService">
		<kuali:kimAttribute name="chartOfAccountsCode" />
		<kuali:kimAttribute name="organizationCode" />
		<kuali:kimAttribute name="accountNumber" />
	</kuali:createKimType>
	
### Roles

#### Create Role

	<kuali:createRole applicationId="KFS" 
					  roleNamespaceCode="KFS-SYS"
					  roleName="" 
					  roleId="" <!-- (optional) -->
					  roleTypeNamespace="KUALI"
					  roleTypeName="Default"
					  roleDescription="" />

#### Delete Role

	<kuali:createRole
					  roleNamespaceCode="KFS-SYS"
					  roleName="" />

#### Add Role Member

	<kuali:addRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="ROLE" memberName="Purchasing User" memberNamespace="KFS-SYS">
		<kuali:attribute name="chartOfAccountsCode" value="3" />
		<kuali:attribute name="organizationCode" value="ACCT" />
	</kuali:addRoleMember>        

---

	<kuali:addRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="PERSON" memberName="sbcortes" activeDate="2014-10-31" />        
---

	<kuali:addRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="GROUP" memberName="ZBUYER1" memberNamespace="KFS-SYS">
		<kuali:attribute name="chartOfAccountsCode" value="3" />
	</kuali:addRoleMember>        


#### Delete Role Member

	<kuali:deleteRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="PERSON" memberName="kellerj" />

---
				
	<kuali:deleteRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="ROLE" memberName="Purchasing User" memberNamespace="KFS-SYS">
		<kuali:attribute name="chartOfAccountsCode" value="3" />
		<kuali:attribute name="organizationCode" value="ACCT" />
	</kuali:deleteRoleMember>        


#### Inactivate Role Member

	<kuali:inactivateRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="PERSON" memberName="sbcortes" />        

---

	<kuali:inactivateRoleMember roleNamespaceCode="KFS-SYS" roleName="ROLE_TEST_1"
			memberType="ROLE" memberName="Purchasing User" memberNamespace="KFS-SYS">
		<kuali:attribute name="chartOfAccountsCode" value="3" />
		<kuali:attribute name="organizationCode" value="ACCT" />
	</kuali:inactivateRoleMember>        


### Permissions

#### Create Permission

    <kuali:createPermission namespaceCode="KFS-SYS" name="Review Ledgers" 
				    		permissionTemplateNamespaceCode="KUALI" 
				    		permissionTemplateName="Default" />
	 
**TODO** : Addition of Attributes		

#### Delete Permission

    <kuali:deletePermission namespaceCode="KFS-SYS" name="Add Ledger Review Note" />
    
#### Assign Permission

	<kuali:assignPermission namespaceCode="KFS-LD" 
							name="View Labor Data" 
							roleNamespaceCode="KFS-SYS" 
							roleName="Fiscal Officer" />
								
#### Delete Permission Assignment

	<kuali:deletePermissionAssignment namespaceCode="KFS-LD" 
									  name="View Labor Data" 
									  roleNamespaceCode="KFS-SYS" 
									  roleName="Fiscal Officer" />

### Responsibilities

#### Create Responsibility

	<kuali:createResponsibility namespaceCode="KFS-SYS" 
								name="Dean/Vice Chancellor Review" 
								documentType="KFS" 
								routeNode="DeanOrViceChancellor" />
      
#### Delete Responsibility

	<kuali:deleteResponsibility namespaceCode="KFS-SYS" 
								name="Dean/Vice Chancellor Review"  />

#### Assign Responsibility

	<kuali:assignResponsibility documentType="KFS" 
								routeNode="DeanOrViceChancellor" 
								roleNamespaceCode="KFS-SYS" 
								roleName="Manager" />
								
#### Delete Responsibility Assignment

	<kuali:deleteResponsibilityAssignment documentType="KFS" 
										  routeNode="DeanOrViceChancellor" 
										  roleNamespaceCode="KFS-SYS" 
										  roleName="Manager" />

Planned Extensions
==================

* KIM Data
	* Roles
		* Modification of Roles
		* Inactivation/Removal of Roles
		* Add Role Member
		* Inactivate Role Member
		* Delete Role Member
	* Permissions
		* Create Permission
		* Modify Permission
		* Inactivate Permission
		* Delete Permission
		* Assign Permission to Role - DONE
		* Delete Role-Permission Link - DONE
		* Inactivate Role-Permission Link
	* Responsibilities
		* Create Responsibility - DONE
		* Assign Responsibility to Role - DONE
		* Delete Role-Responsibility Link - DONE
		* Inactivate Role-Responsibility Link
		* Modify Responsibility
		* Inactivate Responsibility
		* Delete Responsibility
		* Exception Responsibility Creation
	* Create KIM Type - DONE
	* Create KIM Attribute
* KNS Data
	* Update Parameter
	* Remove Parameter
	* Append to Parameter Value
	* Replace Within Parameter Value
* Multi-Database Support (initial work is Oracle-specific)