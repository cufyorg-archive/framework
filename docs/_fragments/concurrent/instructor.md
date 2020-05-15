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
It might been used to make sure both threads are at a specific point to do some operation that requires that.
Such as waiting for a sub-thread to start running so the main thread continue its work.
<br><br>
```java 
    Instructor instructor = new Instructor();
    
    instructor.thread(new Forever(instructor::tick));
    
    //now the result maybe 0 and maybe 1
    int loops = instructor.getLoops().size();
    
    instructor.pair();
    
    //now the result is 1
    loops = instructor.getLoops().size();
```
<br>

- `notify(String)` changes the status of the current loops started by the
instructor, and the next loops that will be started by the instructor in the future.
<br><br>
```java 
    //an instructor that have a running loops
    Instructor instructor = //...;
    
    //with this all the running loops in the instructor will
    //have the `SLEEP` status
    instructor.notify(Loop.SLEEP);

    //also the new added loops will have the `SLEEP` status
    instructor.thread(new Forever());
```
<br>

- `tick(...)` it designed to be used by the members of the instructor.
It makes the caller do the jobs that have been posted to the instructor.
Since the instructor itself don't have it's own thread.
<br><br>
```java 
    //an instructor that have some jobs posted to it
    Instructor instructor = //...;
    
    //this thread will do the jobs of the instructor
    instructor.tick();
    
    //now unless the instructor have repeating posts or another work
    //posted to it, the instructor have no pending work.
```    
<br>

- `post(...)` adds a block of code for the instructor to execute.
The first thread that calls tick() will execute that code.
<br><br>
```java 
    instructor.post((i, l)-> {
        //`i` is the instructor for easy access
        //`l` is the caller loop (maybe null)
        return false; //to remove the post
        //return true; to not remove the post
    });
```
```java 
    instructor.post((i, l)-> {
        //get executed by the first thread calling tick()
        //get executed only if there is currently a loop running
        //in the instructor
        return true;
    }, i -> {
        //get executed by a new thread
        //get executed only if there is no loop running
        //in the instructor
    });
```
```java 
    instructor.post((i, l)-> {
        //get executed by the first thread calling tick()
        //start executing only within the timeout specified
        return true;
    }, i-> {
        //get executed by a new thread
        //get executed only if the timeout passed and no
        //thread started executing the post
    }, 100 /*the timeout (in millis)*/);
```
<br>

- `synchronously(...)` adds a block of code for the instructor to execute.
And make the caller thread wait until the first thread that calls tick()
finishes executing that block of code.
<br><br>
```java 
    instructor.synchronously((i, l)-> {
        //`i` is the instructor for easy access
        //`l` is the caller loop (maybe null)
    });
```
```java 
    instructor.synchronously((i, l)-> {
        //get executed by the first thread calling tick()
        //get executed only if there is currently a loop running
        //in the instructor
    }, i -> {
        //get executed by the caller thread
        //get executed only if there is no loop running
        //in the instructor
    });
```
```java 
    instructor.synchronously((i, l)-> {
        //get executed by the first thread calling tick()
        //start executing only within the timeout specified
    }, i-> {
        //get executed by the caller thread
        //get executed only if the timeout passed and no
        //thread started executing the post
    }, 100 /*the timeout (in millis)*/);
```
<br>

- `isAlive()` tells if the instructor have any running loop or not.
<br><br>
```java 
    Instructor instructor = new Instructor();
    
    //empty instructor always not alive
    assert instructor.isAlive() == false;

    //add a loop to the instructor
    instructor.thread(new Forever(instructor::tick));

    //to make sure that the loop started
    instructor.pair();

    assert instructor.isAlive() == true;
```
<br>

- `join(...)` waits until all the loops in started by the instructor to die.
<br><br>
```java 
    //an instructor with some loops running in
    //different threads than the current
    Instructor instructor = //...;
 
    //let's say that the instructor have running loops
    assert instructor.getLoops().size() != 0;

    //tell the loops to die
    instructor.notify(Loop.BREAK);

    //with this, the thread will sleep until all the
    //loops in the instructor dies
    instructor.join();

    //now all the loops are dead and the instructor have no loops
    assert instructor.getLoops().size() == 0;
```