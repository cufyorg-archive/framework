<p align="center">
  <a href="https://cufyorg.github.io/">
    <img alt="cufy" src="https://github.com/cufyorg/framework/blob/master/cufy.png" width="546">
  </a>
</p>
<p align="center">
  <b>A Framework to be Inherited</b>
</p>
<p align="center">
    <a href="https://jitpack.io/#cufyorg/framework"><img alt="jitpack" src="https://jitpack.io/v/cufyorg/framework.svg"></a>
</p>

The cufy framework is focused on to be more inheritable and more reflection friendly. Making it
less efficient (minor) but more fun and reliable in big complex projects.

## Util
Utils and commons that supports reflection.
<details>
<summary>more details</summary>
    
-   ### Groups
    Collections are a base thing on programming. But it is hard to make a collection foreach
    category a main collection. So it is easier to make the main collection dived it's content for
    us to categories (or subgroups). Unmodifiable group is a group that can't be modified. It is a
    good util to hold constant values.

    Simple Example:

    ```java 
        Group food = new UnmodifibleGroup(Arrays.asList("pizza", "potato", "apple", "orange"));
        Group healthy = food.subgroup("healthy", f -> !f.equals("pizza"));
        Group h = food.subgroup("healthy");
    
        assert healthy == h;
    ```

-   ### Array Util
    It has common array utils and nothing new. But the special thing is the reflection support.
    It can accept 'object' as arrays. All the methods have '0' version of it. The '0' version accept
    object as array parameter.

    Simple example:

    ```java 
        int[] array = {};
        Object object = array;
        List<Integer> arrayAsList = Arrayu.asList(array);
        List objectAsList = Arrayu.asList(object);
    ```

-   ### Collectionu: asList(Map)
    You may need to treat a map as a list. Maybe you want to save storage. Or maybe you want to
    store a list with other values with the same instance. Making a list from a map maybe the
    solution. The method will return a list that it's elements are the values that have positive
    integer keys on the passed map. Those keys is the indexes of the values associated to them.

    ```java 
        List list = Collectionsu.asList(indexedMap);
    ```

</details>

## Util: Function
Functional interfaces.
<details>
    <summary>more details</summary>
    
-   ### Throw Lambdas
    There is always that position. When you want to pass a simple runnable or consumer to some
    method. And that method will invoke it on the same thread. And you don't need to catch 
    exceptions. Since there is a try-catch covering the calling context. So Throw Lambdas will be 
    the saver.

    Simple example:
    ```java 
        try {
        	Runnable runnable = (ThrowRunnable<Exception>) ()-> throwingMethod();
        } catch (Exception e) {
        }
    ```

</details>

## Lang
Base concepts with powerful features.
<details>
    <summary>more details</summary>
</details> 

## Meta
Support for runtime annotations that the program is depending on for it's computations.
<details>
    <summary>more details</summary>
</details> 

## Convert
Converting objects to different types.
<details>
    <summary>more details</summary>
</details>

## Text
Abstracts for formatting, parsing and classifying text.
<details>
    <summary>more details</summary>
</details>

## Text: JSON
So advanced JSON formatter, parser and classifier.
- Uses readers and writers (buffered)
- Completely inheritable and easy to do (syntax and algorithm)
- Supports comments
- Supports recursion
- Can parse to an existing container (List or Map) and it deep override them
- Can specify the type of the input or output using Clazzes
<details>
    <summary>more details</summary>
    
-   ### To parse a json-text:
    
    ```java 
    Object outputObject = JSNO.parse(inputString);
    ```
    
-   ### To format an object to a json-text:
    
    ```java 
    String outputString = JSON.format(inputObject);
    ```
    
-   ### To use more parsing specifications:
    
    ```java 
    JSON.global.parse(inputReader, outputObject, inputClazz, outputClazz);
    ```
-   ### Or if you want auto-classify the input:
    
    ```java 
    JSON.global.cparse(inputReader, outputObject, outputClazz);
    ```
    
-   ### To use more formatting specifications:
    
    ```java 
    JSON.global.format(inputObject, outputObject, inputClazz, outputClazz);
    ```
</details>

## Beans
A bean is a map that it's fields is the properties of it. 
- Compatible anywhere. Since it is a map.
- Any object can be a bean. Just with annotations.
- Interface based. Any class can implement.
- fields tris to convert the value before storing it.
<details>
    <summary>more details</summary>
    
-   ### A simple bean example:
    
    ```java 
        class ExBean extends Bean {
            @Property
            int ex_property;
        }
    ```
    
-   ### A bean for a non-bean instance (fields should be annotated):
    
    ```java 
        Bean.forInstance(theInstance);
    ```
    
-   ### You can override the key (default is a string of field's name) and the type of the property.
    
    ```java 
        @Property(key = @MetaObject("newKey"), type = @MetaClazz(Integer.class))
        int ex_property;
    ```
</details>

## Concurrent
Utils to deal with concurrent actions and infinite loops. All utils depends on the logic rather
than the timing
<details>
    <summary>more details</summary>
</details>

## IO
Utils to deal with Input/Output ports. Like dealing with files or dealing with internet.
<details>
    <summary>more details</summary>
</details>

## IO: Loadable
Objects that can be loaded and saved.
<details>
    <summary>more details</summary>
</details>