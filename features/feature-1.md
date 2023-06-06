# Feature #1: Function Style of creating Nodes
## Metadata
**Author:** Mammad Yahyayev\
**Release:** 3.0.0\
**Status:** In Progress\
**Created:** _06-06-2023 12:00_\
**Updated:** _06-06-2023 12:08_

This upcoming feature will be high in demand, because it is easy to read and use. That will replace large setters and 
getters with fluent chain style. This feature is also beneficial for upcoming features, because adding new methods into
API will be easy.

## Desired Result

```java
root(
    element(
        config(
           name(),
           selector(),
           isLink()
        ),
        filter()
        action()
    ),
    children(
       element(name(), selector()),
       element(name(), selector()),
       element(name(), selector()),
       element(name(), selector()),
       element(name(), selector()),
       element(name(), selector()),
    )
)
```

### Some of Notes
- element method will take default methods such as name and selector to simplify element creation process for nodes
  that requires only name and selector.
- filter and action methods will be implemented in upcoming features.
- each element method has children method.
- name method will take parameters to do some operations in it. For instance, user can decide to specify name in
  syntax like: 'demo name' but he wants to name to be exported as like this: 'demo_name' or 'Demo Name' or 'demo-name'
  in order to do this, name method can take enum (NamingStyle) as second parameter after name given.

```java
name("products count", NamingStyle.MERGE_UNDERSCORE) // result -> products_count
name("products count", NamingStyle.CAPITAL_CASE) // result -> Products Count
```
  **IDEA: maybe user wants to specify multiple style or create its own custom style.**

- selector method will do the same as name method does, it will take selector as the first parameter, and will take
  selector type as enum for the second parameter.

```java
selector("products__i", SelectorType.CLASS) // result -> .products__i
selector("products__i", SelectorType.ID) // result -> #products__i
```
