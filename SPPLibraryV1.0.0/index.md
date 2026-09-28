<section style="text-align: center;">
    <h1>FAQs</h1>
    <p><em>Helper page to learn and revise concepts</em></p>
</section>

- **Create a Sample library Project** : Details [here](#sample-library-file-creation)
    - **Use Library in local projects** : Details [here](#use-library-in-local-projects)

# Sample Library file creation

> please create a java library project (maven based). For now, it will have a utility class to perform arithmetic
> operations on 2 given integers, with basic validations and exceptions. Please share steps and sample code.


Here are the steps to create a Maven-based Java library project with a utility class for arithmetic operations:

---

### **Steps:**

1. **Create Maven Project**
    - Use an IDE (like IntelliJ IDEA or Eclipse) or CLI to create a Maven project.
    - Run:
      ```bash
      mvn archetype:generate -DgroupId=com.example -DartifactId=arithmetic-utils -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
      cd arithmetic-utils
      ```

2. **Update `pom.xml`**
    - Open the `pom.xml` and set the necessary details for your library.
    - Example:
      ```xml
      <project>
      <groupId>com.example</groupId>
      <artifactId>arithmetic-utils</artifactId>
      <version>1.0.0</version>
      <packaging>jar</packaging>
      <name>Arithmetic Utilities</name>
      </project>
      ```

3. **Add Utility Class**
    - Create a package (e.g., `com.example.utils`) and add a utility class.

4. **Add Custom Exceptions**
    - Create custom exceptions for validation errors.

5. **Write Unit Tests**
    - Add unit tests to verify the functionality.

6. **Build the Project**
    - Use `mvn package` to build the library.

---

### **Sample Code**

#### `ArithmeticUtils.java`

```java
package com.example.utils;

public class ArithmeticUtils {

    public static int add(int a, int b) {
        validateInputs(a, b);
        return a + b;
    }

    public static int subtract(int a, int b) {
        validateInputs(a, b);
        return a - b;
    }

    public static int multiply(int a, int b) {
        validateInputs(a, b);
        return a * b;
    }

    public static int divide(int a, int b) {
        validateInputs(a, b);
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }

    private static void validateInputs(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Inputs must be non-negative integers.");
        }
    }
}
```

---

#### `ArithmeticUtilsTest.java`

```java
package com.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArithmeticUtilsTest {

    @Test
    public void testAdd() {
        assertEquals(5, ArithmeticUtils.add(2, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, ArithmeticUtils.subtract(3, 2));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, ArithmeticUtils.multiply(2, 3));
    }

    @Test
    public void testDivide() {
        assertEquals(2, ArithmeticUtils.divide(6, 3));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> ArithmeticUtils.divide(6, 0));
    }

    @Test
    public void testNegativeInputs() {
        assertThrows(IllegalArgumentException.class, () -> ArithmeticUtils.add(-1, 5));
    }
}
```

---

### **Build and Package**

1. Run:
   ```bash
   mvn clean package
   ```
2. The `.jar` file will be created in the `target` directory.

---

### **Publish the Library**

- You can publish the JAR to a Maven repository (e.g., Nexus, JFrog, or Maven Central) if needed.

# Use Library in local projects

> how can i use my jar in my local desktop's other project?

To use your locally built JAR file in another project, follow these steps:

---

### **1. Install the JAR in Local Maven Repository**

You can install your JAR file to your local Maven repository using the following command:

```bash
mvn install:install-file -Dfile=path/to/your/arithmetic-utils-1.0.0.jar \
                         -DgroupId=com.example \
                         -DartifactId=arithmetic-utils \
                         -Dversion=1.0.0 \
                         -Dpackaging=jar
```

- Replace `path/to/your/arithmetic-utils-1.0.0.jar` with the actual path to your JAR file.
- After this, the JAR will be available in your local repository (usually at
  `~/.m2/repository/com/example/arithmetic-utils`).

---

### **2. Add Dependency in Another Project**

In the `pom.xml` of the project where you want to use the library, add the following dependency:

```xml

<dependency>
    <groupId>com.example</groupId>
    <artifactId>arithmetic-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

Now, Maven will fetch the JAR from your local repository.

---

### **3. Add the JAR Directly (Without Maven)**

If you don’t want to use Maven, you can directly add the JAR file to your project:

#### **For Eclipse:**

1. Right-click on your project and go to **Build Path > Configure Build Path**.
2. Go to the **Libraries** tab and click **Add External JARs...**.
3. Select the JAR file and click **OK**.

#### **For IntelliJ IDEA:**

1. Right-click on your project and go to **Open Module Settings**.
2. Under **Dependencies**, click the `+` icon and select **JARs or Directories**.
3. Select the JAR file and click **OK**.

---

### **4. Use the Library**

Once added, you can import and use the utility methods in your project:

#### Example Usage:

```java
import com.example.utils.ArithmeticUtils;

public class Main {
    public static void main(String[] args) {
        int result = ArithmeticUtils.add(10, 20);
        System.out.println("Result: " + result);
    }
}
```

---

### **5. Optional: Sharing the JAR**

If you need to share the JAR with teammates:

1. Send them the JAR file.
2. They can follow the same steps to add it to their projects.


