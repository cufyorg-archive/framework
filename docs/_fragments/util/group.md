---
index: 3
layout: fragment
parent: util
title: Group
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Group.java
    Javadoc: /javadoc/cufy/util/Group.html
description: >-
    Collections are a base thing on programming. But it is hard to
    make a collection foreach category a main collection. So it is
    easier to make the main collection dived it is content for us to
    categories (or subgroups).
---

```java 
    Group food = new HashGroup(Arrays.asList(
        "pizza",
        "potato",
        "apple",
        "orange"
    ));
    
    Group healthy = food.subGroup("healthy", f -> !f.equals("pizza"));
    Group h = food.subGroup("healthy");

    //even if the group 'food' is changed
    //the subgroups will remain equal forever
    assert h.equals(healthy);
```