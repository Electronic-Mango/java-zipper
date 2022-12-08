# Java Zipper

This is a basic Java library providing Python-style "zip" functionality for lists.


## Zipping

"Zip" is an operation converting multiple lists into new lists such as:
```Java
[[1, 2, 3], [4, 5, 6]] => [[1, 4], [2, 5], [3, 6]]
```
Resulting i-th list will have elements at i-th index in the source lists.
You can also think about it as selecting columns from provided rows.

If input lists aren't all the same lenght, then smallest possible subset is selected. For example:
```java
[[1, 2, 3, 4], [5, 6, 7], [8, 9]] => [[1, 5, 8], [2, 6, 9]]
```
Result only has two lists, as that's the size of smallest input list. Above operation is equivalent to zipping: `[[1, 2], [5, 6], [8, 9]]`.

If one of input list is empty (or input is just an empty list), then result will also be an empty list.


## Usage

The library provides three static methods in `Zipper` class, all three used for zipping multiple lists together.
All three will work with lists of any types.
All three will also return new, mutable lists, not a view of the input.

### Zipping list of lists

One way of providing multiple lists for zipping is to provide a list of lists. For example:
```java
final var input = List.of(List.of(1, 2, 3), List.of(1, 2, 3));  // [[1, 2, 3], [1, 2, 3]]
final var output = Zipper.zip(input);  // [[1, 1], [2, 2], [3, 3]]
```


### Zipping using varargs

You can also provide multiple lists as multiple arguments to `zip` method as varargs:
```java
final var output1 = Zipper.zip(List.of("A", "B"), List.of("C", "D"), List.of("E", "F"));
// output1 is [["A", "C", "E"], ["B", "D", "F"]]

final var list1 = List.of('1', '2', '3');
final var list2 = List.of('3', '2', '1');
final var output2 = Zipper.zip(list1, list2);
// output2 is [['1', '3'], ['2', '2'], ['3', '1']]
```


### Zipping using Stream API collector

`Zipper` class also provides method returning Stream API collector.
This collector can be used to collect lists as stream elements:
```java
final output = Stream.of(List.of(1, 1, 1), List.of(2, 2, 2)).collect(Zipper.zipCollector());
// output is [[1, 2], [1, 2], [1, 2]]
```

**Since order of output elements is corelated to order of input elements using unordered and/or parallel streams might might cause unexpected results!**

Calling two previous methods is equivalent to streaming their inputs and using this collector.