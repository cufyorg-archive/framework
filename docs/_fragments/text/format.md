---
index: 3
layout: fragment
parent: text
title: Format
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/Format.java
    Javadoc: /javadoc/cufy/text/Format.html
description: >-
    Is a class that is a classifier, parser, and a formatter at the
    same time. A text language could be represented by a one class.
---

- `cparse(CharSequence)` `cparse(Reader)` classifies the provided text
then parses it to a new instance depending on the result of the
classification.
<br><br>
```java 
    CharSequence sequence = //the sequence
    //Reader reader = //the reader, as a replacement of 'sequence'
    
    //the returned object is the result from c-parsing the text
    Object instance = format.cparse(sequence);
```
<br>

- `cparse(CharSequence, Object)` `cparse(Reader, Object)` classifies
the provided text then parses it to the provided instance (unless it
is not valid) depending on the result of the classification.
<br><br>
```java 
    CharSequence sequence = //the sequence
    //Reader reader = //the reader, as a replacement of 'sequence'
    Object instance = //the instance to try parsing to it

    Object out = format.cparse(sequence, instance);

    //now, if 'instance' is valid, then 'out' will be 'instance'
    //and 'instance' have the value from parsing 'sequence'.
    //otherwise, 'out' will not be 'instance' and 'instance'
    //have not been changed meanwhile 'out' will have the value from
    //parsing 'sequence'
```