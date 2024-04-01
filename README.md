# cbaAPIAssesment

**Quick Overview: **
Framework: Rest Assured + TestNG. 
Language: Java
Report: Extent

**Framework Structure: **
Hierarchy: Base -> Base test -> PetTest

**Packages **
apiEndpoints-> Contains all Pet Endpoints
apiEnums-> Contains all Multiple values
apiConstants-> Contains constants used for test/Helper class
apihelper-> Facilitates for Test class execution
frameworkBase-> Contains the HTTP call methods and establishes the Request/Response specs
framework utils-> Additional customisation for Report, Retry, Config read. 

**Customisations**
Dual testng files, each for Regression/Smoke execution
Retry Listener to retry failed scenarios
Reporting to integrate Assert + Extent report write in a single method for simplicity

**TestRun**
1. Trigger either "testng_Regression" or "testng_smoke" XML based on the need.
2. When running the pom.xml please use the command "mvn test -Dsurefire.suiteXmlFile=<testngXMLName>"
3. To Trigger from CI please use Jenkins
* Have Jenkins installed on your machine
* Click New item -> Enter item name
* Click FreeStyleproject->ok
* select Github project
* select Source code management as Git and Enter the repo URL
* Enter the Branches to build as */Harish-CBATest
* Under Build Steps-Select Invoke top-level maven targets and write goal as "test" with param as "-Dsurefire.suiteXmlFile=<testngXMLName>"

NOTE : The Status brnach has the latest report which includes all the Test cases run. There are total of 10 test cases covering both positive and negative scenarios. 
