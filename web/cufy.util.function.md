# Function

Functional interfaces.

### Throw Lambdas
There is always that position. When you want to pass a simple runnable or consumer to some
method. And that method will invoke it on the same thread. And you don't need to catch 
exceptions. Since there is a try-catch covering the calling context. So Throw Lambdas will be 
the saver.

Simple example:

```java 
    try {
    	Runnable runnable = (ThrowRunnable<Exception>) ()-> throwingMethod();
    } catch (Exception e) {
    }
```