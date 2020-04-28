---
index: 1
layout: page
title: Lang
permalink: lang
links:
    Framework: https://framework.cufy.org/
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/lang
    Javadoc: https://framework.cufy.org/javadoc/cufy/lang/package-summary.html
description: >-
    A package with classes that treated as if it's a part of the language. Such
    Classes are universal and have no main purpose. But deep in the code, It so
    helpful. Specially if it designed that anyone is relying on them.
---

## Clazz
The class instance is a the main data source when it comes to reflection. But 
it's constant and the user can't specify what he exactly wants. So, when it
comes to that, Clazzes may be the saver. A Clazz is simple yet powerful 
instance. It holds information about what is the class it represents, what way
should we respond to that class and what are the component types desired to be.

-   ### Clazz.of(Class, Class, Clazz...)
    This method is the main method getting a clazz to represent the needs of 
    the caller. It accepts what is the treatment (or the "family") should be
    done by reading that clazz. And accepts what is the class that the returned
    clazz should represent (for example, used for constructing a new instance).
    And accepts the component types of the clazz that should be respected.
    ```java 
        //the clazz below should be treated as if it was a Set. and if a new instance
        //needed it should be a new instance of ArrayList. And anyone should really
        //respect that this clazz only allow String elements. 
        Clazz klazz = Clazz.of(Set.class, ArrayList.class, Clazz.of(String.class));
    ```
    
-   ### Clazz.ofa(...)
    This is the default method to resolve the array classes if no component
    types provided. It's used by the method Clazz.of() when that method 
    detects such condition.
-   ### Clazz.ofi(...)
    This method is a shortcut for the method Clazz.of(). It accepts an instance
    and return a clazz suitable for that instance; Null will return a class
    representing the class Void.
    
-   ### Clazz.ofz(...)
    This method is also a shortcut for the method Clazz.of(). It allow getting
    a clazz with part of the data changed than the clazz given to it such as 
    the family or the component types.
    
## DejaVu
The class DejaVu is a representation class that can't be neither instanced.
Nor inherited. It represents a dejaVu action. It could be used as the family
of a Clazz so it tells the method to treat it as a DejaVu

## Empty
The class Empty is a representation class that can't be neither instanced.
Nor inherited. It represents the emptiness. It could be used as the family
of a Clazz so it tells the method to ignore this object is it was not there

## Recurse
The class Recurse is a representation class that can't be neither instanced.
Nor inherited. It represents recursion. It could be used as the family
of a Clazz so it tells the method that a recursion is happening.