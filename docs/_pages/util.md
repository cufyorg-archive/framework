---
index: 0
layout: page
title: Util
permalink: util
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/util
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/package-summary.html
description: >-
    A package of utilities used in the framework. The utilities can be used anywhere
    and it is not designed to work only on the framework. It have been designed help
    to the syntax user and the reflection user. It have a variant type of utils such
    as utils for arrays, collections, readers and also reflection and many more.
---

<br><font color="red">SORRY: THIS SITE STILL UNDER DEVELOPMENT!</font>

## Group
Collections are a base thing on programming. But it is hard to make a
collection foreach category a main collection. So it is easier to make the
main collection dived it's content for us to categories (or subgroups).
```java 
    Group food = new HashGroup(Arrays.asList("pizza", "potato", "apple", "orange"));
    Group healthy = food.subGroup("healthy", f -> !f.equals("pizza"));
    Group h = food.subGroup("healthy");
    //even if the group 'food' is changed, the subgroups will remain equal forever
    assert h.equals(healthy);
```
<br>
<br>

## Arrays
It has common array utils and nothing new. But the special thing is the
reflection support. It can accept 'object' as arrays. All the methods have 
'0' version of it. The '0' version accept object as array parameter.
```java 
    int[] array = {};
    Object object = array;
    
    //if you are a syntax user, this will be the best deal for you!
    List<Integer> arrayAsList = Arrays.asList(array);
    
    //but if you are a reflection user, the array you have could be unsigned
    //and this method will save your time. 
    List objectAsList = Arrays.asList0(object);
```
<br>
<br>

## Collections
The class collection is a utils for collections and maps (and anything from
changing things to collections or changing collections into maps or changing 
things into maps, etc...).
<br>

-   ### `asList(Map)`
You may need to treat a map as a list. Maybe you want to save storage. Or
maybe you want to store a list with other values with the same instance. 
Making a list from a map maybe the solution. The method will return a list
that it's elements are the values that have positive integer keys on the
passed map. Those keys is the indexes of the values associated to them.
```java 
    Map map = new HashMap();

    map.put(8, "value");

    List list = Collections.asList(map);

    assert list.size() == 9;
    assert list.get(8).equals("value");

    list.add("another");

    assert map.get(9).equals("another");

    map.put(10, "different");

    assert list.size() == 11;
    assert list.get(10).equals("different");
```
<br>

-   ### `asMap(Object)`
You may also need to treat public fields of an instance as a keys and
values in a map. This method will return a fixed size map that is reads
from the instance and writes to it. While maintaining the utilities of
the maps.
```java 
    class Test {
        public String key = "value";
    }
    
    Test instance = new Test();

    Map map = Collections.asMap(instance);
    
    assert map.size() == 1;
    assert map.get("key").equals("value");
    
    map.put("key", "another");

    assert instance.key.equals("another");

    instnace.key = "different";

    assert map.get("key").equals("different");
```
<br>

-   ### `asMap(List)`
From the creators of Collections.asMap(List), this is the opposite of the
previous method. It returns a map that don't allow other than nonnegative
integer keys. The map reads directly from the list and writes directly to
it.
```java 
    List list = new ArrayList(Arrays.asList("value"));

    Map map = Collections.asMap(list);

    assert map.size() == 1;
    assert map.get(0).equals("value");

    map.put(2, "another");

    assert list.size() == 3;
    assert list.get(1) == null;
    assert list.get(2).equals("another");

    list.add("different");

    assert map.size() == 4;
    assert map.get(3).equals("different");
```
<br>

-   ### `combine(Iterator...)`
When you have more that one iterator. You may need to iterate each one
separately. Unless you use this method. This method accepts array of
iterators and combines them as one. The returned iterator reads and writes
directly from the iterators.
```java 
    Iterator first = Arrays.asList("abc-".toCharArray()).iterator();
    Iterator second = Arrays.asList("-def".toCharArray()).iterator();

    Iterator combine = Collections.combine(first, second);

    String s = "";
    while(combine.hasNext())
        s += (char) combine.next();

    assert s.equals("abc--def");
```
<br>
<br>

## Throw Lambdas
There is always that position. When you want to pass a simple runnable or
consumer to some method. And that method will invoke it on the same thread.
And you don't need to catch exceptions. Since there is a try-catch covering
the calling context. So Throw Lambdas will be the saver.
```java 
    try {
        //the exception will be catched by the catch statment below and will
        //be the exact exception thrown from the throwingMethod
    	Runnable runnable = (ThrowRunnable<Exception>) ()-> throwingMethod();
    } catch (Exception e) {
    }
```