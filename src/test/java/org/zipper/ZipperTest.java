package org.zipper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZipperTest {
    @Test
    void shouldCorrectlyZipMultipleLists() {
        //given
        final var input = List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9));

        //when
        final var output = Zipper.zip(input);

        //then
        final var expectedOutput = List.of(List.of(1, 4, 7), List.of(2, 5, 8), List.of(3, 6, 9));
        assertEquals(expectedOutput, output);
    }
}