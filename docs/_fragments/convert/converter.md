---
index: 0
layout: fragment
parent: convert
title: Converter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/Converter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/convert/Converter.html
description: >-
    Am abstract of how converters should be. The class is based on
    tokens concept. Each converting operation should have a token that
    have all the variables of that operation. 
---

- `apply(Object, Clazz)` applies the clazz provided to the provided
object. If the provided object or a member of it don't match the
provided clazz, then this method somehow replaces the object (the
given or one of its members) that not match the clazz with another one
but with the same members (exact instance) of the old object.
<br><br>
```java 
    Object[] array = {(int)0, (int) 1};

    converter.apply(
        array, 
        Clazz.of(
            Object[].class,
            Clazz.of(Long.class)
        )
    );
    
    assert array[0].getClass() == Long.class;
    assert array[0].equals(0L);
    assert array[1].getClass() == Long.class;
    assert array[1].equals(0L);
```
<br>

- `clone(Object)` creates a new instance that have the same value and
type as the given object
<br><br>
```java 
    int[][] arr = { {0, 1}, {2, 3} };

    int[][] clone = converter.clone(arr);

    //the arrays are different
    assert arr != clone;
    assert arr[0] != clone[0];
    assert arr[1] != clone[1];

    //but the values are the same
    assert arr[0][0] == clone[0][0];
    assert arr[0][1] == clone[0][1];
    assert clone[1][0] == clone[1][0];
    assert clone[1][1] == clone[1][1];
```
<br>

- `convert(Object, Object)` converts the content of the first object
to the second object. Or if it can't, then it creates a new instance
of the same type of the second object with the content of the first
object.
<br><br>
```java 
    int[][] arr = { {0, 1}, {2, 3} };
    long[][] lng = new long[2][2];

    converter.convert(arr, lng);

    assert ((int) lng[0][0]) == arr[0][0];
    assert ((int) lng[0][1]) == arr[0][1];
    assert ((int) lng[1][0]) == arr[1][0];
    assert ((int) lng[1][1]) == arr[1][1];
```
<br>

- `convert(Object, Object, Clazz)` converts the content of the first
object to the second object while making sure the second object and
the content of the first object are matching the given clazz.
<br><br>
```java 
    int[] array = {0, 1};
    Object[] objects = new Object[2];

    converter.convert(
        array, 
        objects,
        Clazz.of(
            Object[].class,
            Clazz.of(Long.class)
        )
    );

    assert objects[0].getClass() == Long.class;
    assert objects[1].getClass() == Long.class;

    assert objects[0].equals(0L);
    assert objects[1].equals(1L);
```
<br>

- `convert(CovnertToken)` performs the converting operation of the
token given.
<br><br>
```java 
    ConvertToken token = //a token with the converting parameters

    converter.convert(token);

    //now token.output has the result of converting
```