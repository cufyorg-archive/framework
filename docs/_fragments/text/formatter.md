---
index: 2
layout: fragment
parent: text
title: Formatter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/Formatter.java
    Javadoc: /javadoc/cufy/text/Formatter.html
description: >-
    An abstraction of how formatters should be. The class is based on
    tokens concept. Each formatting operation should have a token that
    have all the variables of that operation.
---

- `format(Object)` formats the provided instance into a string.
<br><br>
```java 
    Object instance = //the instance to format

    //the returned string is the result from formatting the instance
    String text = formatter.format(instance);
```
<br>

- `format(Object, Writer)` formats the provided instance and write the
results to the given writer.
<br><br>
```java 
    Object instance = //the instance to format
    Writer writer = //the writer to write the text to

    //the results will be written directly to the writer
    formatter.format(instance, writer);
```
<br>

- `format(FormatToken)` performs the formatting operation of the token
given.
<br><br>
```java 
    FormatToken token = //a token with the formatting parameters

    //the results will be written directly to the writer
    formatter.format(token);
```