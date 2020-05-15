---
beta: true
index: 0
layout: fragment
parent: concurrent
title: Loop
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/concurrent/Loop.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/concurrent/Loop.html
description: >-
    TODO
---

- `While` is the Loop version of the java keyword 'while'.
<br><br>
```java 
    while (something()) {
        doSomething();
    }
```
```java 
    new While(()-> something(), l -> {
        doSomething();
    }).start();
```
<br>

- `For` is the Loop version of the java keyword 'for'.
<br><br>
```java 
    for (int i =0; i < 5; ++i) {
        doSomething(i);
    }
```
```java 
    new For<>(0, i -> i < 5, i -> ++i, (l, i)-> {
        doSomething(i);
    }).start();
```
<br>

- `Foreach` is the Loop version of the java keyword 'for' (foreach).
<br><br>
```java 
    for(int i : new int[3]) {
        doSomething(i);
    }
```
```java 
    new For<>(new int[3], (l, i)-> {
        doSomething(i);
    }).start();
```
<br>

- `Forever` is a loop that runs forever unless stopped.
<br><br>
```java 
    while(true) {
        doSomething();
    }
```
```java 
    new Forever(l-> {
        doSomething();
    }).start();
```
<br>

- `Once` is a loop that runs once.
<br><br>
```java 
    {
        doSomething();
    }
```
```java 
    new Once(l -> {
        doSomething();
    }).start();
```
<br>

- `start(...)` runs the loop in the caller thread
<br>

- `thread(...)` runs the loop in a new thread
<br>

- `pair()` make the caller and the thread running in the loop touch.
This used to make sure the thread of the loop is running.
<br>

- `notify(String)` changes the state of the loop.
<br><br>
```java 
    Loop loop = //... a running loop in another thread

    //pause the loop
    loop.notify(Loop.SLEEP);

    //wakeup the loop
    loop.notify(Loop.CONTINUE);

    //Kill the loop
    loop.notify(Loop.BREAK);
```
<br>

- `append(...)` TODO
<br>

- `post(...)` TODO
<br>

- `synchronously(...)` TODO
<br>

- `isCurrentThread()` determine if the caller thread is the current running thread in the loop.
<br>

- `isAlive()` determines if there is a thread currently running in the loop.
<br>

- `join(...)` make the caller thread wait until the current thread running in the loop finishes the loop.