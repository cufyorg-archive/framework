---
index: 3
layout: fragment
parent: tokens
title: Format Token
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/FormatToken.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/text/FormatToken.html
description:
    The Format Token is a token for a formatting operation witch is writing
    a text that represents a value of a java object to be stored or
    transferred. The token holds the output writer to be written on, the
    input java object, and the environment variables that the formatting
    methods could communicate using it.
---

```java 
    //the parent token.
    FormatToken parent = //...;
    
    //the object to be written as text
    Object instance = //...;
    
    //the writer to write the formatting results on
    Writer writer = //...;
    
    //the clazz of how to format the instance as
    //the clazz's family is used to pick the method
    //the clazz's klass is used to annotate it as the class in the text
    Clazz klazz = //...;
    
    FormatToken token = new FormatToken(parent, instance, writer, klazz);
    //if no parent: new FormatToken(instance, writer, klazz);
    
    assert token.parent == parent;
    assert token.input == instance;
    assert token.output == writer;
    assert token.klazz == klazz;
    
    //now you can pass it to any formatter
    formatter.format(token);
```