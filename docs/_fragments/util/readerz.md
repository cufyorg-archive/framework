---
index: 4
layout: fragment
parent: util
title: Readerz
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Readerz.java
    Javadoc: /javadoc/cufy/util/Readerz.html
description: >-
    Common utilities for readers.
---

- `getRemaining(Reader, int, int)` construct a string from reading the
reader provided until reaching the end of the reader.
<br><br>
```java 
    Reader rdr = new StringReader("Value");

    rdr.read();

    //buffer capacity: 1 ~ maximum array size
    //builder capacity: 1 ~ max array size 
    String remaining = Readerz.getRemaining(
        rdr, 
        /*buffer capacity*/ 100,
        /*builder capacity*/ 500
    );

    assert remaining.equals("alue");
```
<br>

- `getRemaining(Reader, int)` construct a string from reading the
reader provided until reaching the provided string-length limit.
<br><br>
```java 
    Reader rdr = new StringReader("Value");

    rdr.read();

    //read limit: 0 ~ max array size
    String remaining = Readerz.getRemaining(
        rdr, 
        /*read limit*/ 3
    );

    assert remaining.equals("alu");
```
<br>

- `isRemainingEquals(...)` check if the next characters in the reader
equals to one of the strings provided.
<br><br>
```java 
    StringReader rdr = new StringReader("Value");

    //- if trim is true, the whitespaces before
    //and after the sequence will be ignored
    //- if fullRead is true, the strings should
    //equal the string from reading until the end of the reader
    //- if ignoreCase is true, the case of the
    //characters will be ignored
    //- the returned integer represent the index of the string found
    int found = Readerz.isRemainingEquals(
        rdr,
        /*trim*/ false,
        /*fullRead*/ false,
        /*ignoreCase*/ false,
        /*strings*/ "Something", "\nValue", "VALUE", "Valueo", "Value"
    );

    assert found == 3; //Valueo
```