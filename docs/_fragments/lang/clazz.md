---
index: 0
layout: fragment
parent: lang
title: Clazz
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/lang/Clazz.java
    Javadoc: /javadoc/cufy/lang/Clazz.html
description: >-
    The class instance is a main data source when it comes to
    reflection. But it is constant, and the user can't specify what he
    exactly wants. So, when it comes to that, Clazzes may be the
    saver. A Clazz is simple yet powerful instance. It holds
    information about what is the class it represents, what way should
    we respond to that class and what are the component types desired
    to be.
---

- `of(Class, Class, Clazz...)` is the main method getting a clazz to
represent the needs of the caller. It accepts what is the treatment
(or the "family") should be by reading that clazz. And accepts what is
the class that the returned clazz should represent (for example, used
for constructing a new instance). And accepts the component types of
the clazz that should be respected.
<br><br>
```java 
    //the clazz below should be treated as if it was a Set. and if
    //a new instance needed it should be a new instance of ArrayList.
    //And anyone should really respect that this clazz only allow
    //String elements. 
    Clazz klazz = Clazz.of(
        Set.class,
        ArrayList.class,
        Clazz.of(String.class)
    );
```
<br>

- `ofa(...)` is the default method to resolve the array classes if no
component types provided. It is used by the method Clazz.of() when
that method detects such condition.
<br>

- `ofi(...)` is a shortcut for the method Clazz.of(). It accepts an
instance and return a clazz suitable for that instance; Null will
return a class representing the class Void.
<br>

- `ofz(...)` is also a shortcut for the method Clazz.of(). It allows
getting a clazz with part of the data changed than the clazz given to
it such as the family or the component types.