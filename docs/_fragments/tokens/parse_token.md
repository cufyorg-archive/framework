---
index: 2
layout: fragment
parent: tokens
title: Parse Token
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/ParseToken.java
    Javadoc: /javadoc/cufy/text/ParseToken.html
description: >-
    The Parse Token is a token for a parsing operation witch is
    storing the value of a text in a java object so it could accessed
    more easier. The token holds the reader that its text should be
    parsed, the result from parsing, and the environment variables
    that the parsing methods could communicate using it.
---

```java 
    //the parent token.
    ParseToken parent = //...;

    //the reader to parse the text that it holds
    //it should support `mark(int)` and `reset()`
    //and `mark(int)` should not be fixed to the integer given to it
    Reader reader = //...;

    //the initial output.
    //the output to write the parsed values to. 
    //nullable, and get replaced if it is not valid
    Object instance = //...;

    //the clazz of how to parse the text as.
    //the clazz's family is used to pick the method
    //the clazz's klass is used to construct new instance of the value
    //if the value given is null or not valid
    Clazz klazz = //...;
    
    ParseToken token = new ParseToken(parent, reader, instance, klazz);
    //if no parent: new ParseToken(reader, instance, klazz);
    
    assert token.parent == parent;
    assert token.input == reader;
    assert token.output == instance;
    assert token.klazz == klazz;
    
    //now you can pass it to any parser
    parser.parse(token);
```