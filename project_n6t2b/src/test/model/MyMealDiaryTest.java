package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyMealDiaryTest {

    private MyMealDiary myMealDiary;
    private int dailyCal;
    private int totalCals;
    private String date;
    private Meal m1, m2, m3;
    private Ingredient i1, i2, i3, i4, i5;
    private List<Meal> mealList;

    @BeforeEach
    public void setup() {
        myMealDiary = new MyMealDiary();
        this.dailyCal = 0;
        date = null;
        mealList = myMealDiary.getMealDiary();
        this.totalCals = 0;

        m1 = new Meal ("pomodoro pasta", MealType.DINNER);
        m2 = new Meal ("pb toast", MealType.BREAKFAST);
        m3 = new Meal ("apple", MealType.SNACK);

        try {
            i1 = new Ingredient("pene", 200);
            i2 = new Ingredient("pomodoro sauce", 100);
            i3 = new Ingredient("parmesan", 100);

            i4 = new Ingredient("bread", 100);
            i5 = new Ingredient("peanut butter", 100);

            m1.addIngredients(i1);
            m1.addIngredients(i2);
            m1.addIngredients(i3);

            m2.addIngredients(i4);
            m2.addIngredients(i5);
        } catch (InvalidInputException invalidInputException) {
            fail("shouldn't have caught exception");

        }

    }

    @Test
    public void testConstructor() {
        assertEquals(myMealDiary.getDailyCalsLeft(), 0);
        assertTrue(mealList.isEmpty());
        assertEquals(myMealDiary.getDate(), null);
        assertEquals(myMealDiary.getTotalCals(), 0);

    }

    //test add meal to diary
    @Test
    public void testAddOneMealToDiary() {
        assertTrue(mealList.isEmpty());
        myMealDiary.setDailyCalsGoal(2000);
        assertEquals(myMealDiary.getDailyCalsLeft(), 2000);
        myMealDiary.addMealToDiary(m1);
        assertEquals(myMealDiary.getDailyCalsLeft(), 1600);
        assertEquals(myMealDiary.getTotalCals(), 400);
        assertTrue(mealList.contains(m1));

    }

    @Test
    public void testMultipleMealsToDiary() {
        assertTrue(mealList.isEmpty());
        myMealDiary.setDailyCalsGoal(2000);
        assertEquals(myMealDiary.getDailyCalsLeft(), 2000);
        myMealDiary.addMealToDiary(m1);
        myMealDiary.addMealToDiary(m2);
        assertEquals(myMealDiary.getDailyCalsLeft(), 1400);
        assertEquals(myMealDiary.getTotalCals(), 600);
        assertTrue(mealList.contains(m1));
        assertTrue(mealList.contains(m2));



    }


    //test set Daily Cal Goal
    @Test
    public void testSetDailyCalGoal() {
        myMealDiary.setDailyCalsGoal(2000);
        assertEquals(myMealDiary.getDailyCalsLeft(), 2000);
        myMealDiary.setDailyCalsGoal(3000);
        assertEquals(myMealDiary.getDailyCalsLeft(), 3000);
    }


    //test set Today's Date
    @Test
    public void testSetTodaysDate() {
        assertEquals(myMealDiary.getDate(), null);
        myMealDiary.setTodaysDate("July 20, 2021");
        assertEquals(myMealDiary.getDate(), "July 20, 2021");

    }





}
