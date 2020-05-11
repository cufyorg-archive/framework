---
index: 3
name: tokens
layout: page
title: Tokens
permalink: tokens
links:
    GitHub: https://github.com/cufyorg
    Javadoc: https://framework.cufy.org/javadoc/index.html
description: >-
    The tokens are instances that holds information about an
    operation. Such as the type of the operation, the type of
    the data, the input, the output, and the depth of that operation.
---

- `token.parent` is the parent-token of the token. It is the token of
the parent-operation of the operation of the token. It is the way to
transact between a chain of operations that uses tokens.
<br>

- `token.depth` is a final integer represents the number of parents the
token have.
<br><br>
```java 
    int depth = token.depth;

    int i = 0;
    while((token = token.parent) != null)
        i++;

    assert i == depth;
```
<br>

- `token.data` is a map that holds variables within the token only.
It has the variables that should effect its token's behavior only.
<br><br>
```java 
    //sharing data locally to this token specifically
    token.data.put("key", "value");

    //getting data shared locally to this token specifically
    token.data.get("key");
```
<br>

- `token.linear` is a map that holds variables for all of it's children.
It has the variables that should effect its token's and its token's
children and grand-children's behavior.
<br><br>
```java 
    //share data linearly to the next new children only.
    token.linear.put("key", "value");

    //getting data shared linearly from the parent while initializing
    token.linear.get("key");
```
<br>

- `token.tree` is a map that holds variables remotely across all the
tokens related in any kind to its token.
<br><br>
```java 
    //share data globally to all the tokens related to this token
    token.tree.put("key", "value");

    //getting data shared globally from any token related to this token
    token.tree.get("key");
```
<br>