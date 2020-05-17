---
index: 1
layout: fragment
parent: text
title: Parser
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/Parser.java
    Javadoc: /javadoc/cufy/text/Parser.html
description: >-
    An abstract of how parsers should be. The class is based on tokens
    concept. Each parsing operation should have a token that have all
    the variables of that operation.
---

- `parse(CharSequence, Object)` `parse(Reader, Object)` parses the
provided text to the object given.
<br><br>
```java 
    CharSequence sequence = //the sequence
    //Reader reader = //the reader, as a replacement of 'sequence'
    Object instance = //the object to output to

    Object out = parser.parse(sequence, instance);

    //now, if 'instance' is valid, then 'out' will be 'instance'
    //and 'instance' have the value from parsing 'sequence'.
    //otherwise, 'out' will not be 'instance' and 'instance'
    //have not been changed meanwhile 'out' will have the value from
    //parsing 'sequence'
```
<br>

- `parse(ParseToken)` performs the parsing operation of the token
given.
<br><br>
```java 
    ParseToken token = //a token with the parsing parameters

    parser.parse(token);

    //now token.output has the result of the parsing
```