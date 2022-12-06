package org.zipper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Zipper {
    public static <T> List<List<T>> zip(final List<List<T>> lists) {
        if (lists.isEmpty()) {
            return Collections.emptyList();
        }
        final var size = lists.stream().min(Comparator.comparingInt(List::size)).get().size();
        return IntStream.range(0, size).mapToObj(i -> lists.stream().map(list -> list.get(i)).toList()).toList();
    }
}