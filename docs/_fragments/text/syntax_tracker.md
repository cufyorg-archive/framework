---
index: 5
layout: fragment
parent: text
title: SyntaxTracker
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/SyntaxTracker.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/text/SyntaxTracker.html
description: >-
    Is one of the ways to manage the position of the text when dealing
    with readers. 
---

- `construct` to construct a syntax-tracker first the syntax to be
tracked should be specified. The 'nestable' syntax is a fence syntax
that can have inner fence syntax. The 'literal' syntax is a fence that
can't have inner fence syntax. The 'escapable' syntax is a list of
sequences to be skipped when seen in a literal fence.
<br><br>
```java 
    //this is just an example
    Map nestable = new HashMap();

    //brackets is just a wrapper and can have inner fences
    //the key is the starting fence
    //the value is the ending fence
    nestable.put("[", "]");

    Map literal = new HashMap();

    //quotes is sign of a sequence of text and can't have inner fences
    //the key is the starting fence
    //the value is the ending fence
    literals.put("\"", "\"");

    List escapable = new ArrayList();
    
    //backslash is an escaping character and when followed with quote
    //it means that the quote should be treated as a plain text
    //the string should contain the escaped string
    escapable.add("\\\"");

    new SyntaxTracker(nestable, literal, escapable);
```
<br>

- `append(...)` provides the tracker with the next text. When using
a reader this should be called to update the position of the tracker.
<br><br>
```java 
    Reader reader = //... a reader;
    SyntaxTracker tracker = //... a tracker

    int i;
    while((i = reader.read()) != -1) {
        //tracking the previous fences position
        tracker.append((char) i);
        //tracking the current fences position
    }
```
<br>

- `depth()` returns how many fences is applied currently tracked by
the tracker.
<br>

- `fenceStart()` `fenceEnd()` returns the syntax that started the last
applied fence and the syntax that will end it.
<br>