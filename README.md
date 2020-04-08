# Cufy Framework

## Utils and commons
- Reflection compatible
- Independent repository
- Simple and short names

<details>
<summary>More details</summary>
    
-   ### Throw Lambdas
    There is always that position. When you want to pass a simple runnable or consumer to some method. 
    And that method will invoke it on the same thread. And you don't need to catch exceptions. Since 
    there is a try-catch covering the calling context. So Throw Lambdas will be the saver.

    Simple example:
    ```java 
        try {
        	Runnable runnable = (ThrowRunnable<Exception>) ()-> throwingMethod();
        } catch (Exception e) {
        }
    ```

-   ### Groups
    Collections are a base thing on programming. But it is hard to make a collection foreach category a
    main collection. So it is easier to make the main collection dived it's content for us to categories
    (or subgroups). Unmodifiable group is a group that can't be modified. It is a good util to hold
    constant values.

    Simple Example:

    ```java 
        Group food = new UnmodifibleGroup(Arrays.asList("pizza", "potato", "apple", "orange"));
        Group healthy = food.subgroup("healthy", f -> !f.equals("pizza"));
        Group h = food.subgroup("healthy");
    
        assert healthy == h;
    ```

-   ### Array Util
    It has common array utils and nothing new. But the special thing is the reflection support. It can
    accept 'object' as arrays. All the methods have '0' version of it. The '0' version accept object as
    array parameter.

    Simple example:

    ```java 
        int[] array = {};
        Object object = array;
        List<Integer> arrayAsList = Arrayu.asList(array);
        List objectAsList = Arrayu.asList(object);
    ```

-   ### Collectionu: asList(Map)
    You may need to treat a map as a list. Maybe you want to save storage. Or maybe you want to store a
    list with other values with the same instance. Making a list from a map maybe the solution. The 
    method will return a list that it's elements are the values that have positive integer keys on the
    passed map. Those keys is the indexes of the values associated to them.

    ```java 
        List list = Collectionsu.asList(yourMap);
    ```

</details>

### The main dependency for all Cufy Java Repositories.
- Base concepts with powerful features
- Dynamic inheritable classes
- Meta environment support

### Utils to deal with concurrent actions and infinite loops.
- Completely inheritable
- Multi-purpose
- Dependence on the logic rather than the timing

### A bean is a map that it's fields is the properties of it.
- Compatible anywhere. Since it is a map.
- Any object can be a bean. Just with annotations.
- Interface based. Any class can implement.
- fields tris to convert the value before storing it.

### So advanced JSON formatter, parser and classifier
- Uses readers and writers (buffered)
- Completely inheritable and easy to do (syntax and algorithm)
- Supports comments
- Supports recursion
- Can parse to an existing container (List or Map) and it deep override them
- Can specify the type of the input or output using Clazzes

### Utils to deal with Input/Output ports. Like dealing with files or dealing with internet.
- Functional
- Good concept
