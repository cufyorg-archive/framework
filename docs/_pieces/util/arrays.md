---
index: 0
layout: piece
parent: util
title: Arrays
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Arrays.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Arrays.html
description: >-
    It has common array utils and nothing new. But the special thing is the
    reflection support. It can accept 'object' as arrays. All the methods have 
    '0' version of it. The '0' version accept object as array parameter.
---

- `all(...)` `all0(Object, Object)` TODO
<br>

- `any(...)` `any0(Object, Object)` TODO
<br>

- `asList(...)` `asList0(Object)` TODO
<br><br>
```java 
    int[] array = {};
    Object object = array;
    
    //if you are a syntax user, this will be the best deal for you!
    List<Integer> arrayAsList = Arrays.asList(array);
    
    //but if you are a reflection user, the array you have could be unsigned
    //and this method will save your time. 
    List objectAsList = Arrays.asList0(object);
```
<br>

- `copfyOf(...)` `copyOf0(...)` TODO
<br>

- `copyOfRange(...)` `copyOfRange0(...)` TODO
<br>

- `hardcopy(Object, int, Object, int, int)` TODO
<br>

- `merge(...)` `merge0(...)` TODO
<br>

- `sum(...)` `sum0(Object, Object, BiFunction)` TODO