package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Unit {

    private static Random random = new Random();

    private Coordinates coordinates;
    private int fuel;
    private int maxFuel;
    private List<Cargo> cargo;
    private int maxCargoWeight;
    private int currentCargoWeight;

    public Unit(Coordinates startCoordinates, int maxFuel, int maxCargoWeight) {
        this.coordinates = startCoordinates;
        this.maxFuel = maxFuel;
        this.fuel = maxFuel;
        this.maxCargoWeight = maxCargoWeight;
        this.currentCargoWeight = 0;
        this.cargo = new ArrayList<>();
    }

    Coordinates move(int x, int y) {
        if (this.fuel - (x + y) < 0) {
            throw new IllegalStateException("Unit cannot move that far");
        }
        Coordinates coordinatesAfterMove = Coordinates.copy(this.coordinates, x, y);
        this.coordinates = coordinatesAfterMove;
        this.fuel -= (x + y);
        return coordinatesAfterMove;
    }

    void tankUp() {
        int restPoint = random.nextInt(10);
        if (restPoint + this.fuel >= maxFuel) {
            this.fuel = maxFuel;
        } else {
            this.fuel += restPoint;
        }
    }

    void loadCargo(Cargo cargo) {
        if (currentCargoWeight + cargo.getWeight() > maxCargoWeight) {
            throw new IllegalStateException("Can not load any more");
        }
        this.cargo.add(cargo);
        this.currentCargoWeight = calculateCargoWeight();
    }

    void unloadCargo(Cargo cargo) {
        this.cargo.remove(cargo);
        this.currentCargoWeight = calculateCargoWeight();
    }

    void unloadAllCargo() {
        cargo.clear();
        this.currentCargoWeight = 0;
    }

    private int calculateCargoWeight() {
        return cargo.stream().mapToInt(Cargo::getWeight).sum();
    }

    public int getFuel() {
        return this.fuel;
    }

    public int getLoad() {
        return this.currentCargoWeight;
    }

    public List<Cargo> getCargo() {
        return this.cargo;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
