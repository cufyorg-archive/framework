---
index: 2
layout: fragment
parent: concurrent
title: Lock
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/concurrent/Lock.java
    Javadoc: /javadoc/cufy/concurrent/Lock.html
description: >-
    Locking object synchronization is must be in order and sometimes
    gets confusing. And when getting into undefined synchronization
    order. It starts to be impossible to manage it. This class creates
    a thread that locks the object that needed to be lock. And release
    it as needed. And can be controlled concurrently with simple
    method. The downside of this is it uses a whole thread and can't
    rely on it that much since creating useless threads is not good.
    Also if the instance of the lock is not accessible to any control
    thread, the thread will stay forever locking the object and the
    thread will not stop.
---

- `start()` starts the thread of the lock. It just starts the thread 
and don't lock the object. Calling the method twice or calling it
while the thread have been started before will throw an exception.
<br>

- `lock()` starts the thread of the lock if not started. Then, waits
until the thread gain the lock. And `unlock()` releases the lock.
<br><br>
```java 
    //an object to be locked
    Object object = //...;
    
    //this thread will be the only thread that can control the below lock
    Lock lock = new Lock(object);

    //this will wait until the lock locks to the object
    lock.lock();

    //this will wait until the lock releases the object
    lock.unlock();
```
<br>

- `close()` stops the thread of the lock. this method kills the lock 
and the lock will not be usable again.
<br><br>
```java 
    Lock lock = //...;

    lock.close();
    
    //the methods below will throw exceptions
    lock.start();
    lock.lock();
    
    //the methods below will do nothing
    lock.unlock();
    lock.close();
```