package testing;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import testing.extensions.IAExceptionIgnoreExtension;
import testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class MealTest {

    @Spy
    private Meal mealSpy;

    @Test
    void shouldDiscountedPrice() {
        Meal meal = new Meal(35);
        int discountedPrice = meal.getDiscountedPrice(7);
        assertEquals(28, discountedPrice);
        assertThat(28, equalTo(discountedPrice));
    }

    @Test
    void referencesToTheSameObjectShouldBeEqual() {
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;
        assertSame(meal1, meal2);
        assertThat(meal1, sameInstance(meal2));
    }

    @Test
    void referencesToDifferentObjectsShouldNotBeEqual() {
        Meal meal1 = new Meal(10);
        Meal meal2 = new Meal(10);
        assertNotSame(meal1, meal2);
        assertThat(meal1, not(sameInstance(meal2)));
    }

    @Test
    void twoMealsShouldBeEqualWhenPricesAndNameAreTheSame() {
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");
        assertEquals(meal1, meal2);
        assertThat(meal1, is(equalTo(meal2)));
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice() {
        Meal meal = new Meal(10, "Pizza");
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(11));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 18})
    void mealPricesShouldBeLowerThan20(int price) {
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealWithNamePrice")
    void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    private static Stream<Arguments> createMealWithNamePrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("Cheeseburger", 12)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 8})
    void mealPricesShouldBeLowerThan10(int price) {
        if (price > 5) {
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(20));
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestCollection() {
        return Arrays.asList(
                dynamicTest("Dynamic test 1", () -> assertThat(5, lessThan(6))),
                dynamicTest("Dynamic test 2", () -> assertEquals(4, 2 * 2))
        );
    }

    @Tag("fries")
    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal("Hamburger", 10, 2));
        order.addMealToOrder(new Meal("Fries", 7, 4));
        order.addMealToOrder(new Meal("Pizza", 22, 3));
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();
            Meal meal = order.getMeals().get(i);
            Executable executable = () -> {
                assertThat(meal.calculatePrice(price, quantity), lessThan(67));
            };
            String name = "Test name: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);
        }
        return dynamicTests;
    }

    //nie powinno się wywoływać prawdziwych metod na mocku
    @Test
    void testMealSumPrice() {
        //given
        Meal meal = mock(Meal.class);

        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);
        given(meal.sumPrice()).willCallRealMethod();

        //when
        int result = meal.sumPrice();
        //then
        assertThat(result, equalTo(45));
    }

    @ExtendWith(MockitoExtension.class)
    @Test
    void withSpy() {
        //given
//        Meal meal = new Meal("Burrito", 14, 2);
//        Meal mealSpy = spy(meal);
//        Meal mealSpy = spy(Meal.class);

        given(mealSpy.getPrice()).willReturn(15);
        given(mealSpy.getQuantity()).willReturn(3);

        //when
        int result = mealSpy.sumPrice();
        //then
        then(mealSpy).should().getPrice();
        then(mealSpy).should().getQuantity();
        assertThat(result, equalTo(45));
    }


}