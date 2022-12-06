package org.zipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Zipper {
    public static <T> List<List<T>> zip(final List<List<T>> lists) {
        if (lists.isEmpty()) {
            return new ArrayList<>();
        }
        final var size = Collections.min(lists, Comparator.comparingInt(List::size)).size();
        return IntStream.range(0, size)
                .mapToObj(i -> lists.stream().map(list -> list.get(i)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> List<List<T>> zip(final List<T>... lists) {
        return zip(Arrays.asList(lists));
    }

    public static <T> Collector<List<T>, List<List<T>>, List<List<T>>> zipCollector() {
        return Collector.of(ArrayList::new, Zipper::zipCollectorAccumulator, Zipper::zipCollectorCombiner);
    }

    private static <T> void zipCollectorAccumulator(final List<List<T>> target, final List<T> source) {
        if (target.isEmpty()) {
            target.addAll(zip(source));
            return;
        }
        final var size = Math.min(target.size(), source.size());
        target.subList(size, target.size()).clear();
        for (int i = 0; i < size; ++i) {
            target.get(i).add(source.get(i));
        }
    }

    private static <T> List<List<T>> zipCollectorCombiner(final List<List<T>> list1, final List<List<T>> list2) {
        list2.forEach(list -> zipCollectorAccumulator(list1, list));
        return list1;
    }
}