---
index: 0
layout: fragment
parent: concurrent
title: Loop
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/concurrent/Loop.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/concurrent/Loop.html
description: >-
    The loop instance is a way to pass executable code and control its
    execution and edit it concurrently. There is a loop version of almost
    every java code type (while, for, etc...).
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
    new Foreach<>(new int[3], (l, i)-> {
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
This used to make sure the loop is running.
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

- `append(...)` adds a code-block to the code-list of the loop. So the
loop execute it each time it loops. you can take full control of the code
using `getCode()` or `getCode(Consumer)`.
<br>

- `post(...)` adds a block of code for the loop to execute.
The thread of the loop will execute that code when it calls `tick()`.
<br><br>
```java 
    loop.post(l-> {
        //`l` is the loop for easy access
        return false; //to remove the post
        //return true; to not remove the post
    });
```
```java 
    loop.post(l -> {
        //get executed by the thread of the loop
        //get executed only if the loop currently alive
        return true;
    }, l -> {
        //get executed by a new thread
        //get executed only if the loop currently not alive
    });
```
```java 
    loop.post(l -> {
        //get executed by the thread of the loop
        //start executing only within the timeout specified
        return true;
    }, i-> {
        //get executed by a new thread
        //get executed only if the timeout passed and
        //the loop didn't start executing the post
    }, 100 /*the timeout (in millis)*/);
```
<br>

- `synchronously(...)` adds a block of code for the loop to execute.
And make the caller thread wait until the thread of the loop calls tick()
and finishes executing that block of code.
<br><br>
```java 
    loop.synchronously(l -> {
        //`l` is the loop for easy access
    });
```
```java 
    loop.synchronously(l -> {
        //get executed by the thread of the loop
        //get executed only if the loop currently alive
    }, i -> {
        //get executed by the caller thread
        //get executed only if the loop currently not alive
    });
```
```java 
    instructor.synchronously((i, l)-> {
        //get executed by the thread of the loop
        //start executing only within the timeout specified
    }, i-> {
        //get executed by the caller thread
        //get executed only if the timeout passed and
        //the loop didn't start executing the post
    }, 100 /*the timeout (in millis)*/);
```
<br>

- `isCurrentThread()` determine if the caller thread is the current running thread in the loop.
<br>

- `isAlive()` determines if there is a thread currently running in the loop.
<br>

- `join(...)` make the caller thread wait until the current thread running in the loop finishes the loop.