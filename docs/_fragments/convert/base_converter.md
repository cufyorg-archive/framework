---
index: 2
layout: fragment
parent: convert
title: BaseConverter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/BaseConverter.java
    Javadoc: /javadoc/cufy/convert/BaseConverter.html
description: >-
    An implementation of the `AbstractConverter` with the basic
    conversions.
---

- `dejaVu(ConvertToken)` set the output as the output of the object
that have been seen before.
<br>

- `recurse(ConvertToken)` set the output as the output of the object
that have been seen before that is the parent of this object.
<br>

- `collectionToArray(ConvertToken)` converts any collection (or array)
to the array given or a new array with the same klass of the given
clazz.
<br>

- `collectionToCollection(ConvertToken)` converts any collection (or
array) to the collection given or a new collection with same klass of
the given clazz.
<br>

- `collectionToList(ConvertToken)` converts any collection (or array)
to the list given or a new list with the same klass of the given clazz.
<br>

- `mapToMap(ConvertToken)` converts any map to the map given or a new
map with the same klass of the given clazz.
<br>

- `numberToNumber(ConvertToken)` converts any number to one of the
essential number types.
<br>

- `objectToString(ConvertToken)` converts any object to a string
<br>

- `stringToObject(ConvertToken)` converts any string to an object with
the same type as the given clazz.
<br>