---
index: 2
layout: fragment
parent: util
title: Function
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/util/function
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/function/package-summary.html
description: >-
    There is always that position. When you want to pass a simple runnable or
    consumer to some method. And that method will invoke it on the same thread.
    And you don't need to catch exceptions since there is a try-catch covering
    the calling context. So Throw Lambdas will be the saver.
---

```java 
    try {
        //the exception will be catched by the catch statment below and will
        //be the exact exception thrown from the throwingMethod
    	Runnable runnable = (ThrowRunnable<Exception>) ()-> throwingMethod();
    } catch (Exception e) {
    }
```