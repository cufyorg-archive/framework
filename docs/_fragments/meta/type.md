---
index: 2
layout: fragment
parent: meta
title: Type
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/meta/Type.java
    Javadoc: /javadoc/cufy/meta/Type.html
description: >-
    As we can't construct objects. We can't construct clazzes. So this
    annotation is the replacement of the class Clazz.
---

```java 
    //this is clazz of an ArrayList that should be treated
    //as a Set an its component type is String
    @Type(
        value = ArrayList.class,
        family = Set.class,
        componentTypes = String.class
    )
```