---
beta: true
index: 5
name: json
layout: page
title: JSON
permalink: json
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/text/json
    Javadoc: https://framework.cufy.org/javadoc/cufy/text/json/package-summary.html
description: >-
    JSON is a lightweight data-interchange format. It is easy for humans to read 
    and write. It is easy for machines to parse and generate. And this package
    provides it with respect to the abstraction of the package Text.
---

## Why?
- Accept `java.io.Reader` as input
- Accept `java.io.Writer` as output
- Completely inheritable
- Supports comments
- Can parse to an existing container (List or Map) and it deep override them
- Can specify the type of the input or output using Clazzes

## How ?
Different than many JSON parsers. This parser splits the work into multiple stages.
Splitting the work will allow more specifications and allow the user to do some of
the work.