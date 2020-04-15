# JSON

So advanced JSON formatter, parser and classifier.

## Why?
- Accept `java.io.Reader` as input
- Accept `java.io.Writer` as output
- Completely inheritable
- Supports comments
- Supports recursion
- Can parse to an existing container (List or Map) and it deep override them
- Can specify the type of the input or output using Clazzes

## How ?
Different than many JSON parsers. This parser splits the work into multiple stages.
Splitting the work will allow more specifications and allow the user to do some of
the work.

### Tokens
FormatToken, ClassifyToken and ParseToken will tell JSON what is the input, 
where to put the output and what position it is in.

### Format
Format is a method that formats the object presented to it. And writes the
formatted text directly to a writer. And this method require to specify the
clazz of the object (the type of the formatting).

- So, simple formatting would be like this:
```java 
Object obj = ...; //the object to format
Writer out = ...; //the output writer
Clazz type = ...; //the type

//this will format the object as it's type
JSON.global.format(obj, out);

//this will format the object as the type 'type'
JSON.global.format(new FormatToken<>(obj, out, type));
```

- And if you want a string directly (without using writers)
```java 
Object obj = ...; //the object to format
String out = ...; //the output string

//this will format the object as it's type
out = JSON.global.foramt(obj);
```

### Classify
Classify is a method that predict the type of the text presented to it.

- So, a simple classification would be like this:
```java 
Reader rdr = ...; //the reader
Clazz type = ...; //the output

type = JSON.global.classify(rdr);
```

- And if you have a CharSequence and not a reader:
```java 
String str = ...; //the text
Clazz type = ...; //the output

type = JSON.global.classify(str);
```

### Parse
Parse is a method that parses the text presented to it. And this method require 
to specify the clazz of the text (the type of the output). Also you can pass the 
output instance so the method parses directly to it.

- So, a simple parsing would be like this:
```java 
Reader rdr = ...; //the reader
Object out = ...; //the initial output
Clazz type = ...; //the targeted type of the parsed object

//this will parse the text to the type of the output
JSON.global.parse(rdr, out);

//this will parse the text to the type of 'type'
//and 'out' will change if it's not valid
out = JSON.global.parse(new ParseToken<>(rdr, out, type));
```

- And if you have a CharSequence and not a reader:
```java 
String str = ...; //the text
Object out = ...; //the initial output

//this will parse the text to the type of the output
JSON.global.parse(str, out);
```

### CParse
CParse is a method that classify then parse the object dynamically.
It's like a shortcut.

- If you want to classify and parse a string to a java-object.
```java 
String str = ...; //the json string
Object out = ...; //the output java-object

out = JSON.global.cparse(str, out);
```

- And if you want to classify and parse the text in a reader to a java-object.
```java 
Reader rdr = ...; //the reader (should support mark() and reset()!)
Object out = ...; //the output java-object

out = JSON.global.cparse(rdr, out);
```