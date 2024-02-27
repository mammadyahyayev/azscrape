# Feature #3: Increase support of different selectors

## Metadata
**Author:** Mammad Yahya\
**Release:** 3.1.0\
**Status:** Upcoming feature\
**Created:** _27-02-2024 15:28_\
**Updated:** _27-02-2024 15:28_

## Problem

Application only supports for the class-based selection of items. Which isn't enough for complex structured websites
and lacks collecting needed data from websites.

## Selectors

* Class-based
* Id-based
* Tag-based (e.g. tag **\<a>, \<h1>**)
* Attribute-based (e.g., collect all images which have alt attribute)

## Solution

Methods will be provided in the new API to ease the finding element on the UI. Enum type can be used to differentiate 
selectors.

Selenium itself using `By` class to differentiate selectors. E.g.

```java
findElement(By.id("test"))
findElement(By.className("test"))
```

### Approach #1

Selenium `By` class can be used to increase supported selectors. However, this approach will be limited because 
new (custom) selectors will be created to make the application API richer and using `By` class inevitably lead to
creation of new class to make API richer. 

### Approach #2

A new enum type can be created to show the intent of the selectors.

```java
findElement("test", With.CLASS)
findElement("test", With.ID)
findElement("a", With.TAG)
```

In addition to the above, more general approach can be used to pass a selector type, but in this case, the program will try 
to identify the given selector type. In this case, user has to pass . (dot sign) for class selectors, # (for id selectors)
and so on.

```java
findElement("#test")
findElement(".test .test2")
```

The above can be useful for mixed selector type cases as described below:
```java
findElement("#test .test2 .test3")
findElement("#test .test2 .test3", With.MIX)
```
