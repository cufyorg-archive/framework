---
index: 4
layout: fragment
parent: text
title: AbstractFormat
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/text/AbstractFormat.java
    Javadoc: /javadoc/cufy/text/AbstractFormat.html
description: >-
    An abstract that manages and organize the methods of the class
    inheriting it. The sub-class had to just focus on providing
    classifying, formatting, and parsing methods and this class will
    manage everything else using reflection.
---

- `ClassifyMethod` `ParseMethod` `FormatMethod` is an annotations to
be annotated to every classifying, parse, format methods in the
sub-class. The methods annotated with one of these annotations will be
used by the super-class based on the parameters given to the
annotations.
<br><br>
```java 
    @ClassifyMethod
    protected boolean classifyTypeA(ClassifyToken<TypeA> token) {
        //here is the code that classifies the input
        return true; //continue the classification
        //return false; //end the classification
    }
```
```java 
    @ParseMethod(@Filter(includeAll = TypeA.class))
    protected void parseTypeA(ParseToken<TypeA> token) {
        //here is the code that parses the input
    }
```
```java 
    @FormatMethod(@Filter(includeAll = TypeA.class))
    protected void formatTypeA(FormatToken<TypeA> token) {
        //here is the code that formats the input to the output
    }
```
<br>

- `classifyPre(...)` `parsePre(...)` `formatPre(...)` get invoked
before any classifying, parsing, or formatting operation to give the
sub-class control over what should happen before doing the operations.
<br>

- `classify0(...)` `parse0(...)` `format0(...)` are the invokers of
the classifying, parsing, and formatting methods. It manages what 
arguments to pass to the methods, how to extract the exceptions, and
what method is illegal and what not.
<br>

- `classifyElse(...)` `parseElse(...)` `formatElse(...)` get invoked
when no method found matching the requirements of a token.
<br>

- `getClassifyMethods()` returns all the classifying methods in the
class.
<br>

- `getParseMethod(...)` `getFormatMethod(...)` search for a method
that matches the requirements of the token.