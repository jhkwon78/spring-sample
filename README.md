
```
$ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
$ cd my-app 
$ mkdir src\main\resources
```

vi src\main\resources\Spring-Module.xml
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="helloBean" class="com.mycompany.app.HelloWorld">
		<property name="name" value="Mkyong" />
	</bean>
</beans>
```

vi src\main\java\coom\mycompany\app\App.java

```java
package com.mycompany.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(s);
        }
        
        System.out.println( "Hello World!" );
       
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		HelloWorld obj = (HelloWorld) context.getBean("helloBean");
		obj.printHello();
    }
}
```

```
$ mvn package
$ mvn exec:java -Dexec.mainClass=com.mycompany.app.App -Dexec.args="arg1 arg2 arg3"
```
