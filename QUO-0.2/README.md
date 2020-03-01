# JIRA Script to get Sprint Report Data

###### Author: Naman Agrawal (naman.agrawal@quovantis.com)

### Features

- This script will get the JIRA Sprint Report Data and generated report will share over the email.

### Prerequisites
- **JAVA 1.8** and **Chrome** should be installed on the machine.
	- If installed Chrome browser version is above **80.0.3987.116** then you may need to update the Chrome driver according to the installed Chrome browser version.
	- Go to https://chromedriver.chromium.org/ download the zip file and replace **chromedriver.exe** on **\JIRA\QUO-0.2\\deploy\src\main\resources\Drivers** location.

### Configuration
- Open **\JIRA\QUO-0.2\deploy\src\main\resources\DataFiles\Configuration.xlsx** file in Excel
	- In **"Config"** sheet, updated followings:
		- **URL**: This should be the JIRA login page URL.
		- **Project Key**: This should be the JIRA Project Key.
		- **JIRA Email**: A valid JIRA email id
		- **JIRA Password**: A valid JIRA password
		- **Sprint**: This should be actual sprint name
		- **Send Report To**: Report will send to the specified email id
		- **Send Report From**: **Gmail** id of user account which will be used to send the file.
		- **Send Report From Password**: Gmail id account password which will be used to send the file.
	- In **"Columns"** sheet, updated followings:
		- Add all the columns that needs to be add in Sprint Report Data.

### Execution 

- Go to Deployment folder in command prompt using cd command: **cd JIRA\QUO-0.2\deploy**
- Run following command
	- `java -jar QUO-0.2-0.0.1-SNAPSHOT.jar testng.xml`
- A chrome browser will start executing the tests.
- You will see the following text after successful execution of script
	- `Total tests run: 6, Failures: 0, Skips: 0`
- Report will share to user over an email.

### Logs

- Logs will be generated for each execution and can be found on **\JIRA\QUO-0.2\deploy\src\main\java\com\quo\logs** location.

# Release 0.2 | Date: 01 Mar 2020

- "Sprint" value can be passed as a parameter in command prompt. If "Sprint" value is not passed in parameter then it's value will fetch from "Configuration.xlsx" file.
- Command with sprint paramater:
	- `java -jar -Dsprint="Start4: Sprint 34" QUO-0.2-0.0.1-SNAPSHOT.jar testng.xml`
- Command without sprint paramater:
	- `java -jar QUO-0.2-0.0.1-SNAPSHOT.jar testng.xml`
- Fixed element not found issue when selecting the sprint on sprint report page.
- Reduced element waiting time from 20 to 10 seconds.
- Updated email subject and body text