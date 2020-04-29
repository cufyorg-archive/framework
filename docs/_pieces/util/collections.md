---
index: 1
layout: piece
parent: util
title: Collections
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Collections.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Collections.html
description: >-
    The class collection is a utils for collections and maps (and anything from
    changing things to collections or changing collections into maps or changing 
    things into maps, etc...).
---

- `asList(Map)`
You may need to treat a map as a list. Maybe you want to save storage. Or
maybe you want to store a list with other values with the same instance. 
Making a list from a map maybe the solution. The method will return a list
that it's elements are the values that have positive integer keys on the
passed map. Those keys is the indexes of the values associated to them.
<br><br>
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

- `asMap(Object)`
You may also need to treat public fields of an instance as a keys and
values in a map. This method will return a fixed size map that is reads
from the instance and writes to it. While maintaining the utilities of
the maps.
<br><br>
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

- `asMap(List)`
From the creators of Collections.asMap(List), this is the opposite of the
previous method. It returns a map that don't allow other than nonnegative
integer keys. The map reads directly from the list and writes directly to
it.
<br><br>
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

- `combine(Iterator...)`
When you have more that one iterator. You may need to iterate each one
separately. Unless you use this method. This method accepts array of
iterators and combines them as one. The returned iterator reads and writes
directly from the iterators.
<br><br>
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

- `unmodifiableGroup(Group)` TODO