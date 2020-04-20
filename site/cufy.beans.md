# Beans

A bean is a map that it's fields is the properties of it.

## Why?
- Compatible anywhere. Since it is a map.
- Any object can be a bean. Just with annotations.
- Interface based. Any class can implement.
- fields tris to convert the value before storing it.

## How?
To create a bean. You only need to inherit the interface `Bean` but it has not the
full features of the `FullBean`. Speaking of the `FullBean`, it's also an interface.
But you should override some methods or you bean will not work properly. But if you
don't want to override anything. You could inherit `AbstractBean`. It has all the 
features of the `FullBean` and you don't have to override anything. Also it has all
the functionality a typical map have. What? you don't want to inherit anything?
No problem at all. You could still use the full functionality of an `AbstractBean`
on your object with the method `Bean.forInstance`.

- An example of a typical `Bean`:

```java 
    class MyBean extends Bean {
        @Property
        int ex_property;
    }
```

- A bean for a non-bean instance (fields should be annotated):

```java 
    Bean.forInstance(theInstance);
```

- You can override the key (default is a string of field's name) and the type of the property:

```java 
    @Property(key = @MetaObject("newKey"), type = @MetaClazz(Integer.class))
    int ex_property;
```