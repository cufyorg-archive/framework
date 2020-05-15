---
index: 3
layout: fragment
parent: meta
title: Where
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/meta/Where.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/meta/Where.html
description: >-
    Navigating to a field in annotations seems a bit hard. So the
    annotation  where is like the path-map to where a specific field
    located at.
---

```java 
    class SomeWhere {
        //the id tells exactly what field we are looking for
        @Where(id = "here")
        public int myField;
    }
    
    //this will give us the location of the field above
    @Where(
        value = SomeWhere.class,
        id = "here"
    )
```