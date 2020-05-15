---
index: 1
layout: fragment
parent: meta
title: Recipe
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/meta/Recipe.java
    Javadoc: /javadoc/cufy/meta/Recipe.html
description: >-
    Annotations don't allow constructing new objects. But we can pass
    the recipe about how to construct those objects. Giving the source
    string, and the clazz of that object, and a converter to be used
    to convert that object. It is easy not to pass objects using
    annotations.
---

```java 
    //the following object will be an integer with the value 9
    @Recipe(
        value = "9",
        type = @Type(Integer.class)
    )
```