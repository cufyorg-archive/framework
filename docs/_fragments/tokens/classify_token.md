---
index: 1
layout: fragment
parent: tokens
title: Classify Token
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/ClassifyToken.java
    Javadoc: /javadoc/cufy/text/ClassifyToken.html
description: >-
    The Classify Token is a token for a classification operation witch
    is determining the way to parse a text and the type of the result
    from parsing the text. The token holds the reader that its text
    should be classified, the result of the classification, and the
    environment variables that the classification methods could
    communicate using it.
---

```java 
    //the parent token.
    ClassifyToken parent = //...;

    //the reader to classify the text that it holds
    //it should support `mark(int)` and `reset()`
    //and `mark(int)` should not be fixed to the integer given to it
    Reader reader = //...;

    //the clazz to be returned if the classification failed.
    //if null, then a classification-exception will be thrown when
    //the classifier failed to classify
    Clazz klazz = //...;

    ClassifyToken token = new ClassifyToken(parent, reader, klazz);
    //if no parent: new ClassifyToken(reader, klazz);

    assert token.parent == parent;
    assert token.input == reader;
    assert token.output == klazz;

    //now you can pass it to any classifier
    classifier.classify(token);
```