---
index: 0
layout: fragment
parent: util
title: Arrayz
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Arrayz.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Arrayz.html
description: >-
    It has a common array utils and nothing new. The special thing is
    the reflection support. It can accept 'object' as arrays. All the
    methods have '0' version of it. The '0' version accept object as
    array parameter.
---

- `all(...)` checks if an array contains all the given elements.
<br><br>
```java 
    char[] chars = {'a', 'b', 'c', 'd', 'e'};

    assert Arrayz.all(chars, 'a', 'b', 'e');
    assert !Arrayz.all(chars, 'a', 'x');
```
<br>

- `any(...)` checks if an array contains any of the given elements.
<br><br>
```java 
    char[] chars = {'a', 'b', 'c', 'd', 'e'};
    
    assert Arrayz.any(chars, 'x', 'r', 'd', 'm');
    assert !Arrayz.any(chars, 'n', 'm', 'f');
```
<br>

- `asList(...)` get a List that reads and writes to the given array.
<br><br>
```java 
    int[] array = {};
    Object object = array;
    
    //if the array's type is defined
    List<Integer> arrayAsList = Arrayz.asList(array);
    
    //if the array's type is not defined 
    List objectAsList = Arrayz.asList0(object);
```
<br>

- `copfyOf(...)` `copyOfRange(...)` copies the specified range of the
specified array into a new array.
<br><br>
```java 
    int[] array = {0, 1, 2};
    
    Object[] objects = Arrayz.copyOf(i, 2, Object[].class);
    
    assert objects.length == 2;
    assert objects[0] == 0;
    assert objects[1] == 1;
```
<br>

- `hardcopy(Object, int, Object, int, int)` copies elements on the
second array from the first array basic for loop. This method skips
type checks and uses primitive-cast.
<br><br>
```java 
    Integer[] integers = {0, 1, 2};
    short[] shorts = new short[3];
    
    Arrayz.hardcopy(integers, 0, shorts, 0, integers.length);
    
    assert shorts[0] == 0;
    assert shorts[1] == 1;
    assert shorts[2] == 2;
```
<br>

- `merge(...)` merges the provided arrays into one array.
<br><br>
```java 
    int[][] arrays = { {0, 1}, {2}, {3, 4} };
    
    int[] merged = Arrayz.merge(arrays);
    
    assert merged.length == 5;
    assert merged[0] == 0;
    assert merged[2] == 2;
    assert merged[3] == 3;
```
<br>

- `sum(...)` get the result of applying a function while iterating
the items of the provided arrays.
<br><br>
```java 
    int[] array = {0, 1, 2, 3};
    
    int sum = Arrayz.sum(array, /*initial value*/ 0, Integer::sum);
    
    assert sum == 6;
```