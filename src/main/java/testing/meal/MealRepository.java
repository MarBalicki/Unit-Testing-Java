package testing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MealRepository {

    private List<Meal> meals;

    public MealRepository() {
        this.meals = new ArrayList<>();
    }

    public void add(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void remove(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> find(String name, boolean exactMatch, int price, SearchType type) {
        List<Meal> namesMatches = findByName(name, exactMatch);
        return findByPriceWithInitialData(price, type, namesMatches);
    }

    public List<Meal> findByName(String name, boolean exactMatch) {
        List<Meal> result;
        if (exactMatch) {
            result = meals.stream()
                    .filter(meal -> meal.getName().equals(name))
                    .collect(Collectors.toList());
        } else {
            result = meals.stream()
                    .filter(meal -> meal.getName().startsWith(name))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<Meal> findByPrice(int price, SearchType type) {
        return findByPriceWithInitialData(price, type, meals);
    }

    public List<Meal> findByPriceWithInitialData(int price, SearchType type, List<Meal> initialData) {
        List<Meal> result = new ArrayList<>();
        switch (type) {
            case MORE:
                result = initialData.stream()
                        .filter(meal -> meal.getPrice() > price)
                        .collect(Collectors.toList());
                break;
            case EXACT:
                result = initialData.stream()
                        .filter(meal -> meal.getPrice() == price)
                        .collect(Collectors.toList());
                break;
            case LESS:
                result = initialData.stream()
                        .filter(meal -> meal.getPrice() < price)
                        .collect(Collectors.toList());
                break;
        }
        return result;
    }
}
