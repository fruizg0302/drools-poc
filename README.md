## Tech Stack

- **Spring Version**: The version of Spring Boot specified in this POM (`2.0.1.RELEASE`) is relatively old.
- **Java Version**: Java version specified in the properties is `1.8`. My runtime was configure with the following tools:

    ```bash
  java adoptopenjdk-17.0.6+10
  maven 3.6.0
    ```

- **Drools Version**: The Drools version is specified as `7.0.0.Final`.

## Folder structure


```bash
.
├── README.md
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── neo
        │           └── drools
        │               ├── SpringBootDroolsApplication.java
        │               ├── config
        │               │   └── DroolsAutoConfiguration.java
        │               ├── controller
        │               │   └── TestController.java
        │               └── model
        │                   ├── Address.java
        │                   └── fact
        │                       └── AddressCheckResult.java
        └── resources
            ├── application.properties
            └── rules
                └── address.drl.bak
```


## How to run it?


```bash
mvn clean install
mvn spring-boot:run
```


### How a rules file looks like?  


```bash
package plausibcheck.adress

import model.com.paloit.drools.Address;
import fact.model.com.paloit.drools.AddressCheckResult;

rule "Postcode should be filled with exactly 5 numbers"
    when
        address : Address(postcode != null, postcode matches "([0-9]{5})")
        checkResult : AddressCheckResult();
    then
        checkResult.setPostCodeResult(true);
		System.out.println("Verified rule!");
end
```

The given Drools rule is verifying that the postal code (`postcode`) of an `Address` object should be filled with exactly 5 numeric characters.

Here's the breakdown of the rule:

- **When:**
  - `address : Address(postcode != null, postcode matches "([0-9]{5})")`: This part checks that the `postcode` field of the `Address` object is not `null`, and that it matches the regular expression `([0-9]{5})`. This regular expression ensures that the `postcode` consists of exactly 5 numeric characters (digits from 0 to 9).
  - `checkResult : AddressCheckResult()`: This part checks for the presence of an `AddressCheckResult` object, which will be used to store the result of the rule verification.

- **Then:**
  - `checkResult.setPostCodeResult(true)`: If the conditions in the "when" part are met, this action is executed, and the result of the postal code verification is set to `true` in the `AddressCheckResult` object.
  - `System.out.println("Verified!")`: This line prints "Verified!" to the console, indicating that the verification was successful.

This rule verifies that the postal code of an address is exactly 5 digits long, and if so, sets the verification result to `true` and prints a confirmation message.

## How Drools is configured
 
1. **Constants Definition**: The constant `RULES_PATH` is defined, which specifies the path to the rules directory within the classpath.

2. **KieFileSystem Bean**: Defines a Spring Bean for the `KieFileSystem`, which is part of the Drools API for managing the storage of rule files. The method `kieFileSystem()` scans the classpath for rule files under the specified path and writes them into the `KieFileSystem`.

3. **KieContainer Bean**: Defines a Spring Bean for the `KieContainer`, which is used to manage the knowledge bases and sessions in Drools. The method `kieContainer()` builds the Kie modules from the `KieFileSystem` and returns a new `KieContainer` instance with the default release ID.

4. **KieBase Bean**: Defines a Spring Bean for the `KieBase`, representing a knowledge base in Drools. It's used to store all the compiled rules and other related data. The method `kieBase()` simply returns the `KieBase` from the `KieContainer`.

5. **KieSession Bean**: Defines a Spring Bean for the `KieSession`, representing a runtime rule session in Drools. This is where rules are fired against data. The method `kieSession()` returns a new `KieSession` from the `KieContainer`.

6. **KModuleBeanFactoryPostProcessor Bean**: Defines a Spring Bean for the `KModuleBeanFactoryPostProcessor`, a class specific to the integration of Kie (Knowledge Is Everything) modules with Spring. This is likely used to support some particular integration features between Spring and Drools.

7. **Conditional Configuration**: Most of these beans are defined with the annotation `@ConditionalOnMissingBean`, meaning that the bean will only be created if there's no existing bean of the same type already defined in the Spring context. This allows for custom overriding in other parts of the application if needed.

8. **Utilities**: There are some private utility methods, like `getRuleFiles()` and `getKieServices()`, which help with retrieving rule files from the classpath and accessing the `KieServices` singleton, respectively.


## How to test it?

This input
```
curl "http://localhost:8080/test/address?num=5"
```

Should output the following:
```
Verified!
1 rule
Succesful rule verification
```

```
curl "http://localhost:8080/test/address?num=1"
0 rule fullfiled
```


