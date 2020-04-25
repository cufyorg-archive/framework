<html lang="en">
    <head>
        <title>Cufy</title>
        <link type="image/x-icon" rel="icon" href="../cufy.png"/>
    </head>
</html>

The main project of the cufy organization. The framework is written in java.
The framework tries to compete with other frameworks. The cufy framework is
focused on to be more inheritable and more reflection friendly. Making it 
more reliable in big complex projects. The framework still not completely
ready to be used in really big projects since it is still in the beta stage.

-   ## Packages
    The framework is divided onto different packages. Each package allowed to use
    specific packages only. And each package have it's own targets. The following
    is a list of the main packages in this framework.

    -   ### Util
        A package of utilities used in the framework. The utilities can be used
        anywhere and it is not designed to work only on the framework. It have been
        designed help to the syntax user and the reflection user. It have a variant
        type of utils such as utils for arrays, collections, readers and reflection
        and many more.
        -   [Website](util.md)
        -   [Javadoc](javadoc/cufy/util/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/util)

    -   ### Lang
        A package with classes that treated as if it's a part of the language. Such
        Classes are universal and have no main purpose. But deep in the code, It so
        helpful. Specially if it designed that anyone is relying on them.
        -   [Website](lang.md)
        -   [Javadoc](javadoc/cufy/lang/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/lang)

    -   ### Meta
        Annotations are a big part of the java language. It makes the code more
        easier yet powerful. But annotations has so many limitations and can't 
        specify that much data without base annotations to depend on. This package
        provides base annotations that have so many uses.
        -   [Website](meta.md)
        -   [Javadoc](javadoc/cufy/meta/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/meta)

    -   ### Convert
        Java is a type sensitive language. And in complex projects, Sometimes you 
        don't want that complexity. Or sometimes you want to pass a value and you
        want the receiver to convert it to the compatible type. Or you maybe the
        receiver and you want any value to be converted to a specific type. All 
        of that is possible with the convert package.
        -   [Website](convert.md)
        -   [Javadoc](javadoc/cufy/convert/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/convert)

    -   ### Text
        Text is a huge part of any programming language since a lot of data are
        stored and transferred as text. But text should be parsed to be able to
        use it. And data should be formatted to be able to treat it as text.
        This package provide abstract for how the parsing and formatting process
        should be done. And also have some text languages formatters and parsers
        such as JSON.
        -   [Website](text.md)
        -   [Javadoc](javadoc/cufy/text/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/text)

    -   ### JSON
        is a lightweight data-interchange format. It is easy for humans to read 
        and write. It is easy for machines to parse and generate. And this package
        provides it with respect to the abstraction of the package Text.
        -   [Website](json.md)
        -   [Javadoc](javadoc/cufy/text/json/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/text/json)

    -   ### Beans
        Objects have fields. Those fields can't be maneged easily when the class of
        the object is unknown (without reflection). The beans fixes that by treating
        the object as a map and the fields of that object will work as if they're
        the entries of the map. keeping the reflection part hidden from the user.
        -   [Website](beans.md)
        -   [Javadoc](javadoc/cufy/beans/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/beans)

    -   ### Concurrent
        When it comes to concurrent actions. Complexity is all over the place.
        Using instances that manages those concurrent actions is the solution.  
        Those utils helps to deal with concurrent actions and infinite loops.
        And many other concurrent actions. And what makes it special that it
        depends on the logic rather than the timing.
        -   [Website](concurrent.md)
        -   [Javadoc](javadoc/cufy/concurrent/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/concurrent)

    -   ### IO
        Input and Output ports is kind of the purpose of computers. But it needs
        protocols and standards to be able to use it everywhere with the same code.
        This package provides utilities to deal with those protocols and standards
        more easily.
        -   [Website](io.md)
        -   [Javadoc](javadoc/cufy/io/package-summary.html)
        -   [Github](https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/io)