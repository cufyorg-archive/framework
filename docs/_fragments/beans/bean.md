---
index: 0
layout: fragment
parent: beans
title: Bean
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/beans/Bean.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/beans/Bean.html
description: >-
    The base bean class. An abstract for the behavior of any bean.
---

- `inheritance` this interface is ready to be use with no need to
override anything. Annotate any field in the bean to declare it as a
property.
<br><br>
```java 
    class MyBean extends Bean {
        @Property
        int my_property;
    }
```
<br>

- `forInstance(Object)` creates an abstract-bean that reads and writes
to the given object as if it is a bean.
<br><br>
```java 
    Bean.forInstance(theInstance);
```