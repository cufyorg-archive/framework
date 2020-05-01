---
index: 1
layout: fragment
parent: concurrent
title: Instructor
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/concurrent/Instructor.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/concurrent/Instructor.html
description: >-
    A manger that helps maneging a list of loops concurrently.
---

- `start(Loop)` starts the loop provided to it on the caller thread and `thread(Loop)` starts
it in a new thread. The instructor will add the loop to the loops list of it to be later 
removed after the loop finishes. both methods will tick the instructor
after finishing the loop.  
<br><br>
```java 
    Loop loop = //... a loop
    Instructor ins = new Instructor();

    new Thread(()-> ins.start(loop)).start();
    //or: ins.thread(loop);

    ins.pair();

    assert ins.getLoops().size() == 1;

    ins.join();

    //now any post that have been posted have been executed

    assert ins.getLoops().size() == 0;
```
<br>

- `pair()` makes a touch between the caller thread, and the first thread that ticks the instructor.
<br><br>
```java 
```

- `notify(String)` TODO
<br>

- `tick(...)` TODO
<br>

- `post(...)` TODO
<br>

- `synchronously(...)` TODO
<br>

- `isAlive()` TODO
<br>

- `join(...)` TODO