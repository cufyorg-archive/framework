---
index: 0
layout: fragment
parent: meta
title: Filter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/meta/Filter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/meta/Filter.html
description: >-
    You may have a method that can accept number of classes, meanwhile those classes aren't
    related. You may specify those classes one by one, or use the annotation Filter.
    This annotation can accept a range of classes, exclude some, include absolute
    classes making it more useful for dealing with unknown range of types.
---

```java 
    //this filter will include String.class, any Collection, 
    //but not any class extends List.class or the class HashSet
    @Filter(
        in = String.class,
        out = HashSet.class,
        subIn = Collection.class,
        subOut = List.class
    )
```