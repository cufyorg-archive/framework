---
beta: true
index: 0
layout: fragment
parent: convert
title: Converter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/Converter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/convert/Converter.html
description: >-
    Am abstract of how converters should be. The class is based on tokens
    concept. Each converting operation should have a token that have all
    the variables of that operation. 
---

- `apply(Object, Clazz)` applies the clazz provided to the provided
object. If the provided object or a member of it don't match the provided
clazz, then this method somehow replaces the object (the given or one of its members)
that not match the clazz with another one but with the same members (exact instance)
of the old object.
<br><br>
```java 
    TODO
```
<br>

- `clone(Object)` creates a new instance that have the same value and type as the given object
<br><br>
```java 
    TODO
```
<br>

- `convert(Object, Object)` converts the content of the first object to the second object.
Or if it can't, then it creates a new instance of the same type of the second object with
the content of the first object.
<br><br>
```java 
    TODO
```
<br>

- `convert(Object, Object, Clazz)` converts the content of the first object to the second object while
making sure the second object and the content of the first object are matching the given clazz.
<br><br>
```java 
    TODO
```
<br>

- `convert(CovnertToken)` performs the converting operation of the token given.
<br><br>
```java 
    TODO 
```