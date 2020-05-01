---
index: 5
layout: fragment
parent: util
title: Reflection
links:
    GitHub: https://github.com/cufyorg/framework/blob/master/src/main/java/cufy/util/Reflection.java
    Javadoc: https://framework.cufy.org/javadoc/cufy/util/Reflection.html
description: >-
    Utilities helps to deal with reflection and reflection operations.
---

- `asArrayClass(Class)` returns the array class of the provided class.
<br><br>
```java 
    assert Reflection.asArrayClass(int.class) == int[].class;
    assert Reflection.asArrayClass(int[].class) == int[][].class;
    assert Reflection.asArrayClass(int[][].class) == int[][][].class;
```
<br>

- `asObjectClass(Class)` returns the object class of the class provided if it is primitive (or an array of primitive). and returns the provided
class if the provided class is not primitive.
<br><br>
```java 
    assert Reflection.asObjectClass(int.class) == Integer.class;
    assert Reflection.asObjectClass(int[].class) == Integer[].class;
    assert Reflection.asObjectClass(int[][].class) == Integer[][].class;
```
<br>

- `asPrimitiveClass(Class)` return the primitive class of the class provided if it have a primitive class. and returns the provided class if the
provided class is not primitive.
<br><br>
```java 
    assert Reflection.asPrimitive(Integer.class) == int.class;
    assert Reflection.asPrimitive(Integer[].class) == int[].class;
    assert Reflection.asPrimitive(Integer[][].class) == int[][].class;
```
<br>

- `getAllFields(Class)` returns every field in the provided class, and the classes it extends. 
<br><br>
```java 
    class A {
        int a;
    }
    class B extends A {
        int b;
    }

    List<Field> fields = Reflection.getAllFields(B.class);

    assert fields.size() == 2;
    assert fields.get(0).getName().equals("b");
    assert fields.get(1).getName().equals("a");
```
<br>

- `getAllMethods(Class)` returns every method in the provided class (overridden methods not included).
<br><br>
```java 
    class A {
        void a() {}
        void b() {}
    }
    class B extends A {
        @Override
        void a() {}
        void c() {}
    }
    
    List<Method> methods = Reflection.getAllMethod(B.class);
    
    assert methods.size() == 3;
```
<br>

- `ignite(Throwable)` throws any type of throwable without the need to catch it.
<br><br>
```java 
    //no need for catch block
    Reflection.ignite(new Thrwoable());
    Reflection.ignite(new Exception());
    Reflection.ignite(new IOException());
```
<br>

- `overrides(Method, Method)` check if the second method overrides the first method.
<br><br>
```java 
    class A {
        void a() {}
    }
    class B {
        void a() {}
    }
    class C extends A {
        @Override
        void a() {}
    }
    
    Method a = A.class.getMethod("a");
    Method b = B.class.getMethod("a");
    Method c = C.class.getMethod("a");
    
    //any method does override itself
    assert Reflection.overrides(a, a);
    
    //same signature, but in a class that does
    //not override the class of the first method
    assert !Reflection.overrides(a, b);
    
    //a valid override
    assert Reflection.overrides(a, c);
    
    //a super-method do not override a sub-method
    assert !Reflection.overrides(c, a);
```
<br>

- `primitiveCast(Class, Object)` casts the object to the class using the primitive casting.
<br><br>
```java 
    //a primitive casting example
    int a = 9;
    char c = (char) a;
```
```java 
    Integer i = 9;
    char c = Reflection.primitiveCast(Character.class, i);
```
