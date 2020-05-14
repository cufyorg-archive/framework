---
index: 1
layout: fragment
parent: convert
title: AbstractConverter
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/convert/AbstractConverter.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/convert/AbstractConverter.html
description: >-
    An abstract that manages and organize the methods of the class inheriting it.
    The sub-class had to just focus on creating converting-methods and this class
    will manage everything else using reflection.
---

- `ConvertMethod` is an annotation to be annotated to every converting-method in the sub-class. 
The methods annotated with this method will be used by the super-class based on the parameters
given to the annotation.
<br><br>
```java 
    @ConvertMethod(
        input = @Filter(
            //here is the classes that is allowed for input
            includeAll = TypeA.class
        ),
        output = @Filter(
            //here is the classes that is allowed for output
            includeAll = TypeA.class
        )
    )
    protected void typeAToTypeB(ConvertToken<TypeA, TypeB> token) {
        //here is the code that converts the input to the output
    }
```
<br>

- `convertPre(ConvertToken)` gets invoked before any converting operation to give the sub-class
control over what should happen before converting.
<br><br>
```java 
    @Override
    protected boolean convertPre(ConvertToken token) {
        //this is just an example

        //perform the superclass's pre-operations
        if(!super.convertPre(token)) {
            //obey the super class's respond
            return false;
        } else if (token.input == null) {
            //refuse to convert null
            throw new NullPointerException();
        } else if (token.outputClazz.isInstance(token.input)) {
            //take a shortcut
            token.output = token.input;
            //tell not to continue converting
            return false; 
        } else {
            //tell to continue converting
            return true;
        }
    }
```
<br>

- `convert0(Method, ConvertToken)` is the invoker of the converting methods.
It manages what arguments to pass to the method, how to extract the exceptions,
and what method is illegal and what not.
<br>

- `convertElse(ConvertToken)` gets invoked when no method found matching the requirements of the token.
<br><br>
```java 
    @Override
    protected convertElse(ConvertToken token) {
        //this is just an example

        if (token.input == null) {
            token.output = null;
        } else {
            throw new ConvertException();
        }
    }
```
<br>

- `getConvertMethod(Class, Class)` search for a method that matches the requirements of the token.