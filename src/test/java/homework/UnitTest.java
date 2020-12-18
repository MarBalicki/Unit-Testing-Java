package homework;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnitTest {

    @Test
    void unitShouldNotMoveWithoutFuel() {
        Unit unit = new Unit(new Coordinates(0, 0), 0, 20);
        assertThrows(IllegalStateException.class, () -> unit.move(1, 0));
    }

    @Test
    void unitShouldLooseFuelWhenMoving() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        unit.move(10, 2);
        assertThat(unit.getFuel(), is(8));
    }

    @Test
    void movedUnitShouldReturnNewCoordinates() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        Coordinates move = unit.move(10, 2);
        assertThat(move, equalTo(new Coordinates(10, 2)));
    }

    @Test
    void fuelingShouldNotExceedMaxFuelLimit() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        unit.tankUp();
        assertThat(unit.getFuel(), is(20));

    }

    @Test
    void unitCanNotLoadMoreThanMaxCargoWeight() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        unit.loadCargo(new Cargo("Barrels", 10));
        assertThrows(IllegalStateException.class,
                () -> unit.loadCargo(new Cargo("Steel", 11)));
    }

    @Test
    void unloadingAllCargoShouldReduceLoadToZero() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        unit.loadCargo(new Cargo("Wood", 12));
        unit.unloadAllCargo();
        assertThat(unit.getLoad(), is(0));
    }

    @Test
    void cargoShouldNotContainUnloadedCargo() {
        Unit unit = new Unit(new Coordinates(0, 0), 20, 20);
        Cargo steel = new Cargo("Steel", 12);
        unit.loadCargo(steel);
        assertThat(unit.getCargo(), contains(steel));
        unit.unloadCargo(steel);
        assertThat(unit.getCargo(), not(contains(steel)));

    }

}