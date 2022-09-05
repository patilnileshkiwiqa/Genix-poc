# _MTN Automation_

## Prerequisite to run this automation suite:
- JDK 17
- Maven

## Test suites Execution:
- _`Build/Sprint`*`.xml`_ (Sprint-wise test suites)
- _`Build/Regression.xml`_ (ETE Journeys of all sprints)

## How to Execute?
- Clone the project from GitLab repository
- Import all dependencies
- Install all the required plugins
- Navigate to project directory, for example if your project is in folder like `/automation-code`, then navigate to folder using 

```sh
cd /automation-code
```
- Execute below Maven commands for executing the desired test suite.

> Sprint 1:

```sh
mvn clean test -DsuiteXmlFiles=Build/Sprint1.xml
```

> Sprint 2:

```sh
mvn clean test -DsuiteXmlFiles=Build/Sprint2.xml
```

> Sprint 3: 

```sh
mvn clean test -DsuiteXmlFiles=Build/Sprint3.xml
```

> Sprint 4:

```sh
mvn clean test -DsuiteXmlFiles=Build/Sprint4.xml
```

> Sprint 5:

```sh
mvn clean test -DsuiteXmlFiles=Build/Sprint5.xml
```

> Regression:

```sh
mvn clean test -DsuiteXmlFiles=Build/Regression.xml
```

## Report

- Check the automation test report by opening the  _`extReport.html`_  file in the browser.

## TestData for automation

- File name:  _`MTN_ETE_Details.xlsx`_  located at  _`src/main/resources`_  folder


| Sheet Name | Use |
| ---------- | --- |
| `ExecutionDetails` | Contains environment details like URL and language for script execution |
| `Registration` | Contains registration details like Password, OTP, Gender, Communication Language and Communication Preference |
| `Login` | Login details as required to perform ETE Journeys Sprint and test case wise |
| `Plan` | Plan details required in ETE journeys. _(Note: ICCID need to be updated once used)_ |
| `SecureCheckout` | Contains details to be filled on Secure Checkout page while purchasing plan|
| `NonMobility` | Contains details for Non Mobility products test case wise |
| `Dashboard` | Contains details for dashboard items required to perform ETE journeys test case wise |
| `Addons` | Contains details for Addons related ETE journeys test case wise|
| `Usage`, `Notifications`, `TopUp`, `Payments`, `Invoices` | Contains details for performing related ETE journeys test case wise|
| `MyAccount` | Contains details for performing ETE journeys related to My Account module|
| `Support` | Contains details for performing ETE journeys related to Support Ticket|
