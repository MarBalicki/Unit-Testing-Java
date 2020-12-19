package testing.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MealRepositoryTest {

    private MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {
        //given
        Meal meal = new Meal(12, "Pizza");
        //when
        mealRepository.add(meal);
        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {
        //given
        Meal meal = new Meal(12, "Pizza");
        mealRepository.add(meal);
        //when
        mealRepository.remove(meal);
        //then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    @Test
    void shouldBeAbleToFindMealByExactName() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(10, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.findByName("Pizza", true);
        //then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByStartingLetters() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(10, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.findByName("Pi", false);
        //then
        assertThat(result.size(), is(2));
    }

    @Test
    void shouldBeAbleToFindMealByPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(16, "Pizza");
        Meal meal3 = new Meal(20, "Pizza");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        mealRepository.add(meal3);
        //when
        List<Meal> result = mealRepository.findByPrice(12, SearchType.MORE);
        List<Meal> result2 = mealRepository.findByPrice(16, SearchType.LESS);
        List<Meal> result3 = mealRepository.findByPrice(16, SearchType.EXACT);
        List<Meal> result4 = mealRepository.findByPrice(20, SearchType.MORE);
        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(1), is(meal3));
        assertThat(result2.size(), is(1));
        assertThat(result2.get(0), is(meal));
        assertThat(result3.size(), is(1));
        assertThat(result3.get(0), is(meal2));
        assertThat(result4.size(), is(0));
        assertThat(result4, is(empty()));
    }

    @Test
    void shouldBeAbleToFindMealByExactNameAndExactPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(16, "Pizza");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pizza", true, 16, SearchType.EXACT);
        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal2));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterAndExactPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(12, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pi", false, 12, SearchType.EXACT);
        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(meal));
    }

    @Test
    void shouldBeAbleToFindMealByExactNameAndLowerPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(12, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pi", true, 13, SearchType.LESS);
        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal2));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterAndLowerPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(12, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pi", false, 13, SearchType.LESS);
        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(meal));
    }

    @Test
    void shouldBeAbleToFindMealByExactNameAndLHigherPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(12, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pi", true, 11, SearchType.MORE);
        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal2));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterAndLHigherPrice() {
        //given
        Meal meal = new Meal(12, "Pizza");
        Meal meal2 = new Meal(12, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> result = mealRepository.find("Pi", false, 11, SearchType.MORE);
        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(meal));
    }


}
