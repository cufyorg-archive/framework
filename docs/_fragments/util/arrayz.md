---
index: 0
layout: fragment
parent: util
title: Arrayz
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Arrayz.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Arrayz.html
description: >-
    It has a common array utils and nothing new. The special thing is the
    reflection support. It can accept 'object' as arrays. All the methods have 
    '0' version of it. The '0' version accept object as array parameter.
---

- `all(...)` `all0(Object, Object)` checks if an array contains all the given elements.
<br><br>
```java 
    char[] chars = {'a', 'b', 'c', 'd', 'e'};

    assert Arrayz.all(chars, 'a', 'b', 'e');
    assert !Arrayz.all(chars, 'a', 'x');
```
<br>

- `any(...)` `any0(Object, Object)` checks if an array contains any of the given elements.
<br><br>
```java 
    char[] chars = {'a', 'b', 'c', 'd', 'e'};
    
    assert Arrayz.any(chars, 'x', 'r', 'd', 'm');
    assert !Arrayz.any(chars, 'n', 'm', 'f');
```
<br>

- `asList(...)` `asList0(Object)` get a List that reads and writes to the given array.
<br><br>
```java 
    int[] array = {};
    Object object = array;
    
    //if you are a syntax user, this will be the best deal for you!
    List<Integer> arrayAsList = Arrayz.asList(array);
    
    //but if you are a reflection user, the array you have could be unsigned
    //and this method will save your time. 
    List objectAsList = Arrayz.asList0(object);
```
<br>

- `copfyOf(...)` `copyOf0(...)` Get a copy of the provided array.
<br><br>
    int[] i = {0, 1, 2};
    
    Object[] o = Arrayz.copyOf(i, 2, Object[].class);
    
    assert 
<br>

- `copyOfRange(...)` `copyOfRange0(...)` TODO
<br>

- `hardcopy(Object, int, Object, int, int)` TODO
<br>

- `merge(...)` `merge0(...)` TODO
<br>

- `sum(...)` `sum0(Object, Object, BiFunction)` TODO