<html lang="en">
    <head>
        <title>Cufy</title>
        <script>
            window.onload = function() {
              let link = top.document.createElement("link");
              link.type = "image/*";
              link.rel = "icon";
              link.href = "cufy.png";
              top.document.getElementsByTagName("head")[0].appendChild(link);
            };
        </script>
    </head>
</html>

The cufy framework is focused on to be more inheritable and more reflection 
friendly. Making it less efficient (minor) but more fun and reliable in big
complex projects.

### Util
Utils and commons that supports reflection.
[<b>more</b>](web/cufy.util.md)

### Util: Function
Functional interfaces.
[<b>more</b>](web/cufy.util.function.md)

### Lang
Base concepts with powerful features.

## Meta
Support for runtime annotations that the program is depending on for it's
computations.

## Convert
Converting objects to different types.

## Text
Abstracts for formatting, parsing and classifying text.

## Text: JSON
So advanced JSON formatter, parser and classifier.
[<b>more</b>](web/cufy.text.json.md)

## Beans
A bean is a map that it's fields is the properties of it. 
[<b>more</b>](web/cufy.beans.md)

## Concurrent
Utils to deal with concurrent actions and infinite loops. All utils depends on
the logic rather than the timing

## IO
Utils to deal with Input/Output ports. Like dealing with files and internet.

## IO: Loadable
Objects that can be loaded and saved.