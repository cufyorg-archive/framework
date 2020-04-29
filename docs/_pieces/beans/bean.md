---
layout: piece
parent: beans
title: Bean
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/beans/Bean.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/beans/Bean.html
description: >-
    TODO
---

- A bean by inheritance:
<br><br>
```java 
    class MyBean extends Bean {
        @Property
        int ex_property;
    }
```
<br>

- A bean by remote (fields should be annotated):
<br><br>
```java 
    Bean.forInstance(theInstance);
```
<br>
