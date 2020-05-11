---
index: 7
name: beans
layout: page
title: Beans
permalink: beans
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/beans
    Javadoc: https://framework.cufy.org/javadoc/cufy/beans/package-summary.html
description: >-
    Objects have fields, but the fields can't be accessed easily when the class of
    the object unknown (without reflection). The bean standards bypass that by treating
    the object as a map, and the fields of that object will work as the entries of the map, 
    keeping the reflection part hidden from the user.
---

To create a bean. You only need to inherit the interface `Bean` but it has not the
full features of the `FullBean`. Speaking of the `FullBean`, `FullBean` is also an interface,
but you should override some methods. `AbstractBean`has all the features of the `FullBean` and
you don't have to override anything. Also, it has all the functionality of a typical map. What?
you don't want to inherit anything? No problem at all. You could still use the full functionality
of an `AbstractBean` on your object with the method `Bean.forInstance(Object)`.
