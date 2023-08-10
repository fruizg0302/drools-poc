## Tech Stack

- **Spring Version**: The version of Spring Boot specified in this POM (`2.0.1.RELEASE`) is relatively old.
- **Java Version**: Java version specified in the properties is `1.8`. My runtime was configure with the following tools:

    ```bash
    java corretto-8.322.06.4
    maven 3.6.0
    ```

- **Drools Version**: The Drools version is specified as `7.0.0.Final`.

## Folder structure


```bash
 .
├── LICENSE
├── README.md
├── drools-demo-timer-windows
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── hlover
│       │   │           └── demo
│       │   │               ├── DroolsDemoApplication.java
│       │   │               ├── controller
│       │   │               │   └── CEPController.java
│       │   │               └── utils
│       │   │                   └── KieSessionUtils.java
│       │   └── resources
│       │       ├── application.properties
│
```


## How to run it?


```bash
mvn clean install
mvn spring-boot:run
```


How a rules file looks like?  


```bash
package plausibcheck.adress

import com.neo.drools.model.Address;
import com.neo.drools.model.fact.AddressCheckResult;

rule "Postcode should be filled with exactly 5 numbers"
    when
        address : Address(postcode != null, postcode matches "([0-9]{5})")
        checkResult : AddressCheckResult();
    then
        checkResult.setPostCodeResult(true);
		System.out.println("Verified rule!");
end
```

