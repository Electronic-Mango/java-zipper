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
        assertIterableEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyZipRectangularHorizontalMatrix() {
        //given
        final var input = List.of(List.of("1", "2", "3"), List.of("4", "5", "6"));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of("1", "4"), List.of("2", "5"), List.of("3", "6"));
        assertIterableEquals(expectedOutput, output);
    }

    @Test
    void shouldCorrectlyZipRectangularVerticalMatrix() {
        //given
        final var input = List.of(List.of("A", "B"), List.of("C", "D"), List.of("E", "F"));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of("A", "C", "E"), List.of("B", "D", "F"));
        assertIterableEquals(expectedOutput, output);
    }

    @Test
    void shouldZipSingleSublist() {
        //given
        final var input = List.of(List.of(1, 2, 3, 4, 5));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5));
        assertIterableEquals(expectedOutput, output);
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        //when
        final var output = Zipper.zip(Collections.emptyList());

        //then
        assertTrue(output.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfSublistIsEmpty() {
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
        assertIterableEquals(expectedOutput, output);
    }
}