# cbaAPIAssesment

# Quick Overview:
* Framework: Rest Assured 5.4.0 + TestNG 7.9.0. 
* Language: Java 8 
* Report: Extent 5.1.1

# Framework Structure:
Hierarchy: Base -> Base test -> PetTest

# Packages: 
1. apiEndpoints-> Contains all Pet Endpoints
2. apiEnums-> Contains all Multiple values
3. apiConstants-> Contains constants used for test/Helper class
4. apihelper-> Facilitates for Test class execution
5. frameworkBase-> Contains the HTTP call methods and establishes the Request/Response specs
6. framework utils-> Additional customisation for Report, Retry, Config read. 
7. apiModels -> Contains the Request and Response JSON Models

# Customisations
1. Dual testng files, each for Regression/Smoke execution
2. Retry Listener to retry failed scenarios
3. Reporting to integrate Assert + Extent report write in a single method for simplicity

# TestRun
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
