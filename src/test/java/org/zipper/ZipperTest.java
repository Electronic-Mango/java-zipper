package org.zipper;

import org.junit.jupiter.api.Test;

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
        final var output = Zipper.zip(Collections.emptyList());

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
}