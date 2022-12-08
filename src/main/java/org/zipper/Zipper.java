package org.zipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

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
    private Zipper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Zip provided list of lists. Equivalent to:
     * <pre>
     *     lists.stream().collect(Zipper.zipCollector());
     * </pre>
     * Resulting list is a new list, not a view of the input.
     * <p>
     * See {@link Zipper} for more details regarding zipping.
     *
     * @param lists list of lists to zip
     * @param <T>   type stored in provided lists
     * @return zipped input, as a new list
     */
    public static <T> List<List<T>> zip(final List<List<T>> lists) {
        return lists.stream().collect(zipCollector());
    }

    /**
     * Zip provided lists. Lists can be provided as an array, or as multiple arguments. Equivalent to:
     * <pre>
     *     Arrays.stream(lists).collect(Zipper.zipCollector());
     *     Zipper.zip(Arrays.asList(lists));
     * </pre>
     * Resulting list is a new list, not a view of the input.
     * <p>
     * See {@link Zipper} for more details regarding zipping.
     *
     * @param lists multiple lists to zip, or an array of lists
     * @param <T>   type stored in provided lists
     * @return zipped input, as a new list
     */
    @SafeVarargs
    public static <T> List<List<T>> zip(final List<T>... lists) {
        return Arrays.stream(lists).collect(zipCollector());
    }

    /**
     * Stream API collector which can be used for zipping stream of lists. For example:
     * <pre>
     *     Stream.of(List.of(1, 2, 3), List.of(4, 5, 6)).collect(zipCollector());
     * </pre>
     * will result in [[1, 4], [2, 5], [3, 6]].
     * <p>
     * Result of collecting is a new list of lists.
     *
     * @param <T> type stored in lists in stream
     * @return collector used to zipping lists as Stream API elements
     */
    public static <T> Collector<List<T>, List<List<T>>, List<List<T>>> zipCollector() {
        return Collector.of(ArrayList::new, Zipper::zipCollectorAccumulator, Zipper::zipCollectorCombiner);
    }

    private static <T> void zipCollectorAccumulator(final List<List<T>> target, final List<T> source) {
        if (target.isEmpty()) {
            target.addAll(source.stream().map(List::of).map(ArrayList::new).toList());
            return;
        }
        final var size = Math.min(target.size(), source.size());
        target.subList(size, target.size()).clear();
        for (int i = 0; i < size; ++i) {
            target.get(i).add(source.get(i));
        }
    }

    private static <T> List<List<T>> zipCollectorCombiner(final List<List<T>> list1, final List<List<T>> list2) {
        final var size = Math.min(list1.size(), list2.size());
        list1.subList(size, list1.size()).clear();
        for (int i = 0; i < size; ++i) {
            list1.get(i).addAll(list2.get(i));
        }
        return list1;
    }
}