package homework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinatesTest {

    @Test
    void checkTheCorrectPositionOfArgumentsInTheCoordinatesConstructor() {
        Coordinates coordinates = new Coordinates(10, 40);
        assertThat(coordinates.getX(), is(10));
        assertThat(coordinates.getY(), is(40));
    }

    @Test
    void coordinatesInRange0And100ShouldNotThrowException() {
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                assertThat(coordinates.getX(), equalTo(i));
                assertThat(coordinates.getY(), equalTo(j));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createCoordinates")
    void whenCoordinatesAreOutOfRangeThenThrowIllegalArgumentException(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(x, y));
    }

    private static Stream<Arguments> createCoordinates() {
        return Stream.of(
                Arguments.of(-1, 10),
                Arguments.of(1, -10),
                Arguments.of(-12, -1),
                Arguments.of(101, 102)
        );
    }

    @Test
    void copyShouldReturnNewInstance() {
        Coordinates coordinates = new Coordinates(10, 10);
        Coordinates copy = Coordinates.copy(coordinates, 0, 0);
        assertThat(copy, not(sameInstance(coordinates)));
        assertThat(copy, equalTo(coordinates));
        assertThat(copy.hashCode(), equalTo(coordinates.hashCode()));
    }

    @Test
    void copyShouldReturnAddCoordinates() {
        Coordinates coordinates = new Coordinates(10, 10);
        Coordinates copy = Coordinates.copy(coordinates, 5, 6);
        assertThat(copy.getX(), equalTo(15));
        assertThat(copy.getY(), equalTo(16));
    }

    @Test
    void ifCopyLeaveRange0And100ShouldThrowIllegalArgumentException() {
        Coordinates coordinates = new Coordinates(0, 100);
        assertThat(coordinates, is(notNullValue()));
        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(coordinates, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(coordinates, 0, 1));
    }



}