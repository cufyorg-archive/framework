# Util

Utils and commons that supports reflection.

### Groups
Collections are a base thing on programming. But it is hard to make a collection foreach
category a main collection. So it is easier to make the main collection dived it's content for
us to categories (or subgroups). Unmodifiable group is a group that can't be modified. It is a
good util to hold constant values.

Simple Example:

```java 
Group food = new UnmodifibleGroup(Arrays.asList("pizza", "potato", "apple", "orange"));
Group healthy = food.subgroup("healthy", f -> !f.equals("pizza"));
Group h = food.subgroup("healthy");

assert healthy == h;
```

### Array Util
It has common array utils and nothing new. But the special thing is the reflection support.
It can accept 'object' as arrays. All the methods have '0' version of it. The '0' version accept
object as array parameter.

Simple example:

```java 
    int[] array = {};
    Object object = array;
    List<Integer> arrayAsList = Arrayu.asList(array);
    List objectAsList = Arrayu.asList(object);
```

### Collectionu: asList(Map)
You may need to treat a map as a list. Maybe you want to save storage. Or maybe you want to
store a list with other values with the same instance. Making a list from a map maybe the
solution. The method will return a list that it's elements are the values that have positive
integer keys on the passed map. Those keys is the indexes of the values associated to them.

```java 
    List list = Collectionsu.asList(indexedMap);
```