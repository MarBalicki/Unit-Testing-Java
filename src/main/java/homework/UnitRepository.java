package homework;

import java.util.HashMap;
import java.util.Map;

public class UnitRepository {

    private Map<Coordinates, Unit> units;

    public UnitRepository() {
        this.units = new HashMap<>();
    }

    void addUnit(Unit unit) {
        this.units.put(unit.getCoordinates(), unit);
    }

    void removeUnit(Unit unit) {
        this.units.remove(unit.getCoordinates());
    }

    void removeUnit(Coordinates coordinates) {
        this.units.remove(coordinates);
    }

    Unit getUnitByCoordinates(Coordinates coordinates) {
        return units.get(coordinates);
    }
}
