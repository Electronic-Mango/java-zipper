package org.electronicmango.zipper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZipperTest {
    @Test
    void shouldCorrectlyZipSquareMatrix() {
        //given
        final var input = List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of(1, 4, 7), List.of(2, 5, 8), List.of(3, 6, 9));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyZipRectangularHorizontalMatrix() {
        //given
        final var input = List.of(List.of("1", "2", "3"), List.of("4", "5", "6"));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of("1", "4"), List.of("2", "5"), List.of("3", "6"));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyZipRectangularVerticalMatrix() {
        //given
        final var input = List.of(List.of("A", "B"), List.of("C", "D"), List.of("E", "F"));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of("A", "C", "E"), List.of("B", "D", "F"));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldZipSingleSublist() {
        //given
        final var input = List.of(List.of(1, 2, 3, 4, 5));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldZipToEmptyListForEmptyInput() {
        //when
        final var output = Zipper.zip(Collections.<List<Integer>>emptyList());

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldZipToEmptyListIfSublistIsEmpty() {
        //given
        final var input = List.of(List.of(1.1, 2.2, 3.3), List.of(4.4, 5.5, 6.6), Collections.<Double>emptyList());

        //when
        final var output = Zipper.zip(input);

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldUseSmallestSubsetForZipping() {
        //given
        final var input = List.of(List.of('A', 'B', 'C'), List.of('D', 'E'), List.of('F'));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of('A', 'D', 'F'));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyCollectRectangularVerticalMatrix() {
        //given
        final var input = List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6));

        //when
        final var output = input.stream().collect(Zipper.zipCollector());

        //then
        final var expectedOutput = List.of(List.of(1, 3, 5), List.of(2, 4, 6));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyCollectHorizontalMatrix() {
        //given
        final var input = List.of(List.of(1.1, 2.2, 3.3), List.of(4.4, 5.5, 6.6));

        //when
        final var output = input.stream().collect(Zipper.zipCollector());

        //then
        final var expectedOutput = List.of(List.of(1.1, 4.4), List.of(2.2, 5.5), List.of(3.3, 6.6));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldUseSmallestSubsetForCollecting() {
        //given
        final var input = List.of(List.of('A'), List.of('B', 'C'), List.of('D', 'E', 'F'));

        //when
        final var output = input.stream().collect(Zipper.zipCollector());

        //then
        final var expectedOutput = List.of(List.of('A', 'B', 'D'));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCollectToEmptyListIfSublistIsEmpty() {
        //given
        final var input = List.of(List.of("Q", "W"), List.of("E"), List.<String>of());

        //when
        final var output = input.stream().collect(Zipper.zipCollector());

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldReturnMutableListOnZip() {
        //given
        final var input = List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6));

        //when
        final var output = Zipper.zip(input);

        //then
        assertDoesNotThrow(() -> output.forEach(List::clear));
        assertDoesNotThrow(output::clear);
    }

    @Test
    void shouldReturnMutableListOnCollect() {
        //given
        final var input = List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6));

        //when
        final var output = input.stream().collect(Zipper.zipCollector());

        //then
        assertDoesNotThrow(() -> output.forEach(List::clear));
        assertDoesNotThrow(output::clear);
    }

    @Test
    void shouldCorrectlyVarargZipSquareMatrix() {
        //when
        final var output = Zipper.zip(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9));

        //then
        final var expectedOutput = List.of(List.of(1, 4, 7), List.of(2, 5, 8), List.of(3, 6, 9));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyVarargZipRectangularHorizontalMatrix() {
        //when
        final var output = Zipper.zip(List.of("1", "2", "3"), List.of("4", "5", "6"));

        //then
        final var expectedOutput = List.of(List.of("1", "4"), List.of("2", "5"), List.of("3", "6"));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyVarargZipRectangularVerticalMatrix() {
        //when
        final var output = Zipper.zip(List.of("A", "B"), List.of("C", "D"), List.of("E", "F"));

        //then
        final var expectedOutput = List.of(List.of("A", "C", "E"), List.of("B", "D", "F"));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldVarargZipSingleSublist() {
        //when
        final var output = Zipper.zip(List.of(1, 2, 3, 4, 5));

        //then
        final var expectedOutput = List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldVarargZipToEmptyListIfSublistIsEmpty() {
        //when
        final var output = Zipper.zip(List.of(1.1, 2.2, 3.3), List.of(4.4, 5.5, 6.6), Collections.emptyList());

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldUseSmallestSubsetForVarargZipping() {
        //when
        final var output = Zipper.zip(List.of('A', 'B', 'C'), List.of('D', 'E'), List.of('F'));

        //then
        final var expectedOutput = List.of(List.of('A', 'D', 'F'));
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldVarargZipToEmptyListForEmptyInput() {
        //when
        final var output = Zipper.zip();

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldReturnMutableListOnVarargZip() {
        //when
        final var output = Zipper.zip(List.of(1, 2), List.of(3, 4), List.of(5, 6));

        //then
        assertDoesNotThrow(() -> output.forEach(List::clear));
        assertDoesNotThrow(output::clear);
    }

    @Test
    void shouldCorrectlyCollectParallelStream() {
        //given
        final var input = List.of(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0),
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
        );

        //when
        final var output = input.stream().parallel().collect(Zipper.zipCollector());

        //then
        final var expectedOutput = List.of(
                List.of(1, 1, 1, 1, 1),
                List.of(2, 2, 2, 2, 2),
                List.of(3, 3, 3, 3, 3),
                List.of(4, 4, 4, 4, 4),
                List.of(5, 5, 5, 5, 5),
                List.of(6, 6, 6, 6, 6),
                List.of(7, 7, 7, 7, 7),
                List.of(8, 8, 8, 8, 8),
                List.of(9, 9, 9, 9, 9),
                List.of(0, 0, 0, 0, 0)
        );
        assertEquals(expectedOutput, output);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenOneOfListsIsNull() {
        //given
        final var input = new ArrayList<List<Integer>>();
        input.add(List.of(1, 2, 3));
        input.add(null);
        input.add(List.of(4, 5, 6));

        //when
        assertThrows(NullPointerException.class, () -> Zipper.zip(input));
    }

    @Test
    void shouldThrowNullPointerExceptionForNullArgument() {
        //when
        assertThrows(NullPointerException.class, () -> Zipper.zip((List<List<Object>>) null));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenOneOfVarargListsIsNull() {
        //when
        assertThrows(NullPointerException.class, () -> Zipper.zip(List.of('A', 'B'), null));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenOneOfCollectedElementsIsNull() {
        //given
        final var input = new ArrayList<List<Integer>>();
        input.add(List.of(1, 2, 3));
        input.add(null);
        input.add(List.of(4, 5, 6));

        //when
        assertThrows(NullPointerException.class, () -> input.stream().collect(Zipper.zipCollector()));
    }
}