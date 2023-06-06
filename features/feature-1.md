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
 DataNode node = new DataNode.Builder()
        .name("product")
        .selector(".product__i")
        .children(
            new Element()
                  .name("name")
                  .selector(".product__name")
                  .action(ActionOp.trimValue()),
            new Element()
                  .name("category")
                  .selector(".product__category")
                  .action(ActionOp.convertNameTo(NamingStyle.UPPERCASE)),
            new Element()
                  .name("owner")
                  .selector(".product__owner")
                  .action(el -> el.getName().toLowerCase())
        )
        .build();
```

### Some of Notes
- Each Element can be declared with chaining and with constructor if it requires only simple construction.
- Each Element has also children.
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
