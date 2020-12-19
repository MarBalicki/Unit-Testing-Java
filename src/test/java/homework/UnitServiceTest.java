package homework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @InjectMocks
    private UnitService unitService;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private UnitRepository unitRepository;

    @Test
    void addedCargoShouldBeLoadedOnUnit() {
        //given
        Unit unit = new Unit(new Coordinates(0, 0), 10, 10);
        Cargo cargo = new Cargo("Steel", 5);
        given(cargoRepository.findCargoByName("Steel")).willReturn(Optional.of(cargo));
        //when
        unitService.addCargoByName(unit, "Steel");
        //then
        verify(cargoRepository).findCargoByName("Steel");
        assertThat(unit.getLoad(), is(5));
        assertThat(unit.getCargo().get(0), equalTo(cargo));
    }

    @Test
    void shouldThrowExceptionIfNoCargoIsFoundToAdd() {
        //given
        Unit unit = new Unit(new Coordinates(0, 0), 10, 10);
        given(cargoRepository.findCargoByName("Steel")).willReturn(Optional.empty());
        //when
        //then
        assertThrows(NoSuchElementException.class,
                () -> unitService.addCargoByName(unit, "Steel"));
    }

    @Test
    void shouldReturnUnitByCoordinates() {
        //given
        Coordinates coordinates = new Coordinates(5, 7);
        Unit unit = new Unit(coordinates, 12, 10);
        given(unitRepository.getUnitByCoordinates(coordinates)).willReturn(unit);

        //when
        Unit result = unitService.getUnitOn(new Coordinates(5, 7));
        //then
        assertThat(result, sameInstance(unit));
        assertThat(result.getCoordinates(), equalTo(coordinates));
    }

    @Test
    void shouldThrowExceptionIfUnitNotFound() {
        //given
        Coordinates coordinates = new Coordinates(0, 0);
        given(unitRepository.getUnitByCoordinates(coordinates)).willReturn(null);
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> unitService.getUnitOn(coordinates));
    }

}