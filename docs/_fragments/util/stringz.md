---
index: 6
layout: fragment
parent: util
title: Stringz
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Stringz.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Stringz.html
description: Utilities for strings
---

- `all(String, CharSequence...)` returns the first missing queries from the
provided queries in the provided string. 
<br><br>
```java
    String string = "abcd";
    
    CharSequence query = Stringz.all(string, "bc", "x", "ab", "r");
    
    assert query.equals("x");
```
```java 
    String string = "abcd";

    CharSequence query = Stringz.all(string, "a", "bc", "d");

    assert query == null;
```
<br>

- `any(String, CharSequence...)` returns the first query from the 
provided query in the provided string.
<br><br>
```java 
    String string = "abcd";

    CharSequence query = Stringz.any(string, "x", "y", "ab", "d");

    assert query.equals("ab");
```
```java
    String string = "abcd";
    
    CharSequence query = Stringz.any(string, "x", "y", "z");

    assert query == null;
```
<br>

- `repeat(...)` construct a string from repeating the provided string
the provided times and append between them the delimiter (if provided).
<br><br>
```java
    String string = "a";

    String repeated = Stringz.repeat(string, /*times*/ 3);

    assert repeated.equals("aaa");
```
```java
    String string = "a";
    String delimiter = "|";

    String repeated = Stringz.repeat(string, delimiter, /*times*/ 3);

    assert repeated.equals("a|a|a");
```