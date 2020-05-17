---
index: 0
layout: fragment
parent: text
title: Classifier
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/Classifier.java
    Javadoc: /javadoc/cufy/text/Classifier.html
description: >-
    An abstract of how classifiers should be. The class is based on
    tokens concept. Each classifying operation should have a token
    that have all the variables of that operations.
---

- `classify(CharSequence)` `classify(Reader)` returns the clazz of the
text provided.
<br><br>
```java 
    CharSequence sequence = //the sequence
    //Reader reader = //the reader, as a replacement of 'sequence'
    
    //the returned clazz is the result from classifying the text
    Clazz klazz = classifier.classify(sequence);
```
<br>

- `classify(ClassifyToken)` performs the classifying operation of the
token given.
<br><br>
```java 
    ClassifyToken token = //a token with the classifying parameters

    classifier.classify(token);

    //now token.output has the result of the classification
```