<html lang="en">
    <head>
        <title>Cufy</title>
        <script>
            window.onload = function() {
              let link = top.document.createElement("link");
              link.type = "image/*";
              link.rel = "icon";
              link.href = "../cufy.png";
              top.document.getElementsByTagName("head")[0].appendChild(link);
            };
        </script>
    </head>
</html>

# Meta
Annotations are a big part of the java language. It makes the code more
easier yet powerful. But annotations has so many limitations and can't 
specify that much data without base annotations to depend on. This package
provides base annotations that have so many uses.

-   ## @Filter
    You may have a method that can accept number of classes and those classes are not
    related. You may specify those classes one by one, or use the annotation Filter.
    This annotation can accept a range of classes, exclude some, include absolute
    classes making it more useful for dealing with unknown range of types.
    ```java 
        //this filter will include String.class, any Collection, 
        //but not any class extends List.class or the class HashSet
        @Filter(
            in = String.class,
            out = HashSet.class,
            subIn = Collection.class,
            subOut = List.class
        )
    ```
    
-   ## @Type
    As we can't construct objects. We can't construct clazzes. So the this annotation
    is the replacement of the class Clazz. 
    ```java 
        //this is clazz of an ArrayList that should be treated as a Set an its 
        //component type is String
        @Type(
            value = ArrayList.class,
            family = Set.class,
            componentTypes = String.class
        )
    ```

-   ## @Where
    Navigating to a field in annotations seems a bit hard. So the annotation where
    is like the path-map to where a specific field located at.
    ```java 
    class SomeWhere {
        //the id tells exactly what field we are looking for
        @Where(id = "here")
        public int myField;
    }
    
    //this will give us the location of the field above
    @Where(
        value = SomeWhere.class,
        id = "here"
    )
    ```

-   ## @Recipe
    Annotations don't allow constructing new objects. But we can pass the recipe
    about how to construct those objects. Giving the source string, and the clazz
    of that object and a converter to be used to convert that object. It is easy
    not to pass objects using annotations.
    ```java 
        //the following object will be an integer with the value 9
        @Recipe(
            value = "9",
            type = @Type(Integer.class)
        )
    ```