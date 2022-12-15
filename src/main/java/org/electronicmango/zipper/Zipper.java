package org.electronicmango.zipper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class providing various static methods performing Python-style "zip" operation on multiple lists of elements.
 * For example, converting lists [[1, 2, 3], [4, 5, 6]] into [[1, 4], [2, 5], [3, 6]].
 * Effectively it will select columns from provided 2D arrangement.
 * <p>
 * If not all lists are of the same size, then smallest possible subset is selected.
 * For example, in [[1, 2, 3, 4], [5, 6, 7], [8, 9]] smallest list has size 2, which results in [[1, 5, 8], [2, 6, 9]].
 * It will be equivalent to zipping [[1, 2], [5, 6], [8, 9]].
 * <p>
 * If any of inner list has size 0, or empty list is provided, then result will be an empty list.
 */
public final class Zipper {
    private Zipper() { }

    /**
     * Zip provided list of lists. For example:
     * <pre>
     *     final var list = List.of(List.of(1, 2, 3), List.of(4, 5, 6));
     *     Zipper.zip(list);
     * </pre>
     * will result in [[1, 4], [2, 5], [3, 6]].
     * <p>
     * Resulting list is a new, mutable list, not a view of the input.
     * <p>
     * See {@link Zipper} for more details regarding zipping.
     * <p>
     * This method will throw {@link NullPointerException} if one of sub-lists is null, or if main argument is null.
     *
     * @param lists list of lists to zip
     * @param <T>   type stored in provided lists
     * @return zipped input, as a new list
     * @throws NullPointerException when either one of sub-lists is null, or if main argument is null
     */
    public static <T> List<List<T>> zip(final List<? extends List<T>> lists) {
        Objects.requireNonNull(lists);
        lists.forEach(Objects::requireNonNull);
        final int resultSize = lists.stream().mapToInt(List::size).min().orElse(0);
        return IntStream.range(0, resultSize)
                .mapToObj(i -> lists.stream().map(list -> list.get(i)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Zip provided lists. Lists can be provided as an array, or as multiple arguments. Equivalent to:
     * <pre>
     *     Zipper.zip(Arrays.asList(lists));
     * </pre>
     * Resulting list is a new, mutable list, not a view of the input.
     * <p>
     * See {@link Zipper} for more details regarding zipping.
     * <p>
     * This method will throw {@link NullPointerException} if one of received lists is null.
     *
     * @param lists multiple lists to zip, or an array of lists
     * @param <T>   type stored in provided lists
     * @return zipped input, as a new list
     * @throws NullPointerException when either one of received lists is null
     */
    @SafeVarargs
    public static <T> List<List<T>> zip(final List<T>... lists) {
        return zip(Arrays.asList(lists));
    }

    /**
     * Stream API collector which can be used for zipping stream of lists. For example:
     * <pre>
     *     Stream.of(List.of(1, 2, 3), List.of(4, 5, 6)).collect(zipCollector());
     * </pre>
     * will result in [[1, 4], [2, 5], [3, 6]].
     * <p>
     * Using this collector is equivalent to collecting stream to list and calling {@link Zipper#zip(List)}:
     * <pre>
     *     Collectors.collectingAndThen(Collectors.toList(), Zipper::zip);
     * </pre>
     * <p>
     * Result of collecting is a new, mutable list of lists.
     * <p>
     * <b>Since order of output elements is correlated with order of input elements
     * using unordered and/or parallel streams might cause unexpected results!</b>
     * <p>
     * Returned collector will throw {@link NullPointerException} is one of stream elements is null.
     *
     * @param <T> type stored in lists in stream
     * @return collector used to zipping lists as Stream API elements
     */
    public static <T> Collector<List<T>, ?, List<List<T>>> zipCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), Zipper::zip);
    }
}
