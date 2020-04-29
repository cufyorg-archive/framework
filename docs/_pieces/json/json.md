---
index: 0
layout: piece
parent: json
title: JSON
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/json/JSON.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/text/json/JSON.html
description: >-
    The Format class for JSON. This class have the parse, format and classify
    methods that is required to deal with JSON.
---

- `format(...)` is a method that formats the object presented to it. And
writes the formatted text directly to a writer. And this method require to
specify the clazz of the object (the type of the formatting).
<br><br>
```java 
    Object obj = ...; //the object to format
    Writer out = ...; //the output writer
    Clazz type = ...; //the type
    
    //this will format the object as it's type
    JSON.global.format(obj, out);
    
    //this will format the object as the type 'type'
    JSON.global.format(new FormatToken<>(obj, out, type));
```
Or if you want a string directly (without using writers):
<br>
```java 
    Object obj = ...; //the object to format
    String out = ...; //the output string
    
    //this will format the object as it's type
    out = JSON.global.foramt(obj);
```
<br>

- `classify(...)` is a method that predict the type of the text presented to it.
<br><br>
```java 
    Reader rdr = ...; //the reader
    Clazz type = ...; //the output
    
    type = JSON.global.classify(rdr);
```
Or if you have a CharSequence and not a reader:
<br>
```java 
    String str = ...; //the text
    Clazz type = ...; //the output
    
    type = JSON.global.classify(str);
```
<br>

- `parse(...)` is a method that parses the text presented to it. And this
method require to specify the clazz of the text (the type of the output).
Also you can pass the output instance so the method parses directly to it.
<br><br>
```java 
    Reader rdr = ...; //the reader
    Object out = ...; //the initial output
    Clazz type = ...; //the targeted type of the parsed object
    
    //this will parse the text to the type of the output
    JSON.global.parse(rdr, out);
    
    //this will parse the text to the type of 'type'
    //and 'out' will change if it's not valid
    out = JSON.global.parse(new ParseToken<>(rdr, out, type));
```
Or if you have a CharSequence and not a reader:
<br>
```java 
    String str = ...; //the text
    Object out = ...; //the initial output
    
    //this will parse the text to the type of the output
    JSON.global.parse(str, out);
```
<br>

- `cparse(...)` is a method that classify then parse the object dynamically.
It's like a shortcut.
<br><br>
```java 
    String str = ...; //the json string
    Object out = ...; //the output java-object
    
    out = JSON.global.cparse(str, out);
```
Or if you want to classify and parse the text in a reader to a java-object:
<br>
```java 
    Reader rdr = ...; //the reader (should support mark() and reset()!)
    Object out = ...; //the output java-object
    
    out = JSON.global.cparse(rdr, out);
```