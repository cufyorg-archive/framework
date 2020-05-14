---
beta: true
index: 1
layout: fragment
parent: convert
title: AbstractConverter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/AbstractConverter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/convert/AbstractConverter.html
description: >-
    An abstract that manages and organize the methods of the class inheriting it.
    The sub-class had to just focus on creating converting-methods and this class
    will manage everything else using reflection.
---

- `ConvertMethod` is an annotation to be annotated to every converting-method in the sub-class. 
The methods annotated with this method will be used by the super-class based on the parameters
given to the annotation.
<br><br>
```java 
    TODO
```
<br>

- `convertPre(ConvertToken)` gets invoked before any converting operation to give the sub-class
control over what should happen before converting.
<br><br>
```java 
    TODO
```
<br>

- `convert0(Method, ConvertToken)` is the invoker of the converting methods.
It manages what arguments to pass to the method, how to extract the exceptions,
and what method is illegal.
<br><br>
```java 
    TODO 
```
<br>

- `convertElse(ConvertToken)` gets invoked when no method found matching the requirements of the token.
<br><br>
```java 
    TODO
```
<br>

- `getConvertMethod(Class, Class)` search for a method that matches the requirements of the token.
<br><br>
```java 
    TODO
```