---
beta: true
index: 2
layout: fragment
parent: convert
title: BaseConverter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/BaseConverter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/convert/BaseConverter.html
description: >-
    An implementation of the `AbstractConverter` with the basic conversions.
---

- `dejaVu_object(ConvertToken)` set the output as the output of the
object that have been seen before.
<br><br>
```java 
    TODO
```
<br>

- `recurse_object(ConvertToken)` set the output as the output of the
object that have been seen before that is the parent of this object.
<br><br>
```java 
    TODO
```
<br>

- `collection_array(ConvertToken)` converts any collection (or array)
to the array given or a new array with the same klass of the given clazz.
<br><br>
```java 
    TODO
```
<br>

- `collection_collection(ConvertToken)` converts any collection (or array)
to the collection given or a new collection with same klass of the given clazz.
<br><br>
```java 
    TODO
```
<br>

- `collection_list(ConvertToken)` converts any collection (or array)
to the list given or a new list with the same klass of the given clazz.
<br><br>
```java 
    TODO
```
<br>

- `map_map(ConvertToken)` converts any map to the map given or a new map
with the same klass of the given clazz.
<br><br>
```java 
    TODO 
```
<br>

- `number_(ConvertToken)` converts any number to one of the essential number types.
<br><br>
```java 
    TODO 
```
<br>

- `object_string(ConvertToken)` converts any object to a string
<br><br>
```java 
    TODO
```
<br>

- `string_object(ConvertToken)` converts any string to an object with
the same type as the given clazz.
<br><br>
```java 
    TODO 
```