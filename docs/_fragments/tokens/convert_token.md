---
index: 0
layout: fragment
parent: tokens
title: Convert Token
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/ConvertToken.java
    Javadoc: /javadoc/cufy/convert/ConvertToken.html
description: >-
    The Convert Token is a token for object converting operation witch
    is converting the value of an object from a class to a different
    class. The token holds the input object, the output object, the
    input type, the output type, and the environment variables that
    the converting methods could communicate using it.
---

```java 
    //the parent token
    ConvertToken parent = //...;

    //the input object that have the value to be converted
    Object in = //...;

    //the output object to convert the value to
    //it will be replaced if it is null or invalid
    Object out = //...;

    //the clazz of the input object
    //the clazz's family is used to pick the method
    //the clazz's class is used as the class of the input 
    Clazz inClz = //...;

    //the clazz of the output object
    //the clazz's family is used to pick the method
    //the clazz's class is used to construct new product
    //if th product given is null or not valid
    Clazz outClz = //...;

    ConvertToken token = new ConvertToken(parent,in,out,inClz,outClz);
    //if no parent: new ConvertToken(in, out, inClz, outClz);

    assert token.parent == parent;
    assert token.input == in;
    assert token.output == out;
    assert token.inputClazz == inClz;
    assert token.outputClazz = outClz;

    //now you can pass it to any converter
    converter.convert(token);
```