package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {

    private Meal m1, m2, m3, m4;
    private List<Ingredient> ingredients;
    private Ingredient i1, i2, i3, i4, i5, i6;


    @BeforeEach
    public void setUp() {
        m1 = new Meal ("pomodoro pasta", MealType.DINNER);
        m2 = new Meal ("pb toast", MealType.BREAKFAST);
        m3 = new Meal ("apple", MealType.SNACK);
        m4 = new Meal ("BLT", MealType.LUNCH);

        try {

            i1 = new Ingredient("pene", 200);
            i2 = new Ingredient("pomodoro sauce", 100);
            i3 = new Ingredient("parmesan", 100);
        } catch (InvalidInputException e) {
           fail("shouldn't have caught exception");
        }

        ingredients = new ArrayList<>();
       // m1.addIngredients(i1);
        // m1.addIngredients(i2);
        //m1.addIngredients(i3);

    }


    //test meal constructor
    @Test
    public void testMealConstructor() {
        assertEquals(m2.getMealName(), "pb toast");
        assertEquals(m2.getType(), MealType.BREAKFAST);
        assertTrue(m2.getIngredients().isEmpty());

    }

    // test add one ingredient to one meal
    @Test
    public void testAddOneIngredientToOneMeal() {
        assertTrue(m1.getIngredients().isEmpty());
        m1.addIngredients(i1);
        assertEquals(m1.getIngredients().size(), 1);
        //assertTrue(m1.mealContains(i1));
        assertEquals(m1.getIngredients().get(0), i1);


    }

    //test add multiple ingredients to a meal
    @Test
    public void testAddMultipleIngredientsToSameMeal() {
        assertTrue(m1.getIngredients().isEmpty());
        m1.addIngredients(i1);
        m1.addIngredients(i2);
        assertEquals(m1.getIngredients().get(0), i1);
        assertEquals(m1.getIngredients().get(1), i2);
        assertEquals(m1.getIngredients().size(), 2);

    }


    //test add ingredients to different meals
    @Test
    public void testAddIngredientsToDiffMeals() {
        assertTrue(m1.getIngredients().isEmpty());
        assertTrue(m2.getIngredients().isEmpty());
        m1.addIngredients(i1);
        m2.addIngredients(i2);
        assertEquals(m1.getIngredients().size(), 1);
        assertEquals(m2.getIngredients().size(), 1);
        assertTrue(m1.getIngredients().contains(i1));
        assertTrue(m2.getIngredients().contains(i2));
    }


    //test search ingredient that is in the list
    @Test
    public void testSearchIngredients() {
       m1.addIngredients(i1);
       m1.addIngredients(i2);
       assertTrue(m1.getIngredients().contains(i1));
       assertTrue(m1.getIngredients().contains(i2));
       assertTrue(m1.searchIngredients("pene"));
       assertTrue(m1.searchIngredients("pomodoro sauce"));
    }


    //test search ingredient that doesn't exist
    @Test
    public void testSearchIngredientNotFound() {
        assertFalse(m1.getIngredients().contains(i1));
        assertFalse(m1.searchIngredients("pene"));
    }

    @Test
    public void testCalcCalories() {
        m1.addIngredients(i1);
        m1.addIngredients(i2);
        m1.addIngredients(i3);
        assertEquals(m1.calcCalories(), 400);
        m2.addIngredients(i1);
        m2.addIngredients(i2);
        assertEquals(m2.calcCalories(), 300);
        assertEquals(m2.getMealCals(), 300);
    }






}
