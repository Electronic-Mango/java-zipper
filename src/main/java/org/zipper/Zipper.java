package org.zipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class Zipper {
    public static <T> List<List<T>> zip(final List<List<T>> lists) {
        if (lists.isEmpty()) {
            return Collections.emptyList();
        }
        final var size = Collections.min(lists, Comparator.comparingInt(List::size)).size();
        return IntStream.range(0, size).mapToObj(i -> lists.stream().map(list -> list.get(i)).toList()).toList();
    }

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
        final var resultingList = List.copyOf(list1.subList(0, size));
        for (int i = 0; i < size; ++i) {
            resultingList.get(i).addAll(list2.get(i));
        }
        return resultingList;
    }
}