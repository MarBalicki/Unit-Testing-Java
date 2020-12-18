package homework;

import java.util.Objects;

public class Cargo {

    private String name;
    private int weight;

    public Cargo(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return weight == cargo.weight &&
                Objects.equals(name, cargo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }
}
