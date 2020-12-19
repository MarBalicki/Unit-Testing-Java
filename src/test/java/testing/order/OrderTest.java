package testing.order;

import testing.extensions.BeforeAfterExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import testing.meal.Meal;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("Before each");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("After each");
        order.cancel();
    }

    @Test
    void testAssertArrayEquals() {
        int[] ints1 = {1, 2, 3};
        int[] ints2 = {1, 2, 3};

        assertArrayEquals(ints1, ints2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), is(equalTo(0)));
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        Meal meal = new Meal(12, "Spagetti");
        Meal meal2 = new Meal(12, "Sandwich");
        order.addMealToOrder(meal);
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals().get(0).getPrice(), is(equalTo(12)));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        Meal meal = new Meal(12, "Spagetti");
        Meal meal2 = new Meal(12, "Sandwich");
        order.addMealToOrder(meal);
        order.addMealToOrder(meal2);
        order.removeMealFromOrder(meal);
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), not(contains(meal)));
        assertThat(order.getMeals(), contains(meal2));
    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        Meal meal1 = new Meal(12, "Spagetti");
        Meal meal2 = new Meal(12, "Sandwich");
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        assertThat(order.getMeals(), contains(meal1, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
    }

    @Test
    void testIfTwoMealsAreTheSame() {
        Meal meal1 = new Meal(12, "Spagetti");
        Meal meal2 = new Meal(12, "Sandwich");
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal2);
        List<Meal> meals3 = Arrays.asList(meal2, meal1);

        assertThat(meals1, is(meals2));
        assertThat(meals2, is(not(meals3)));
    }

    @Test
    void orderTotalPriceShouldNotExceedsMaxIntValue() {
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Spagetti");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        assertThrows(IllegalStateException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqualZero() {
        //Order is created in BeforeEach
        assertThat(order.totalPrice(), is(0));
    }

    @Test
    void cancellingOrderShouldRemoveAllItemsFromMealsList() {
        Meal meal1 = new Meal(12, "Spagetti");
        Meal meal2 = new Meal(12, "Sandwich");
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        order.cancel();
        assertThat(order.getMeals().size(), is(0));
    }


}