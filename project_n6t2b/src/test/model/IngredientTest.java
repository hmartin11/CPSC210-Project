package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IngredientTest {

    private Ingredient i1;

    @Test

    public void testIngredientConstructor() {
        try {
        i1 = new Ingredient("cashew milk", 30);
        assertEquals(i1.getIngredient(), "cashew milk");
        assertEquals(i1.getCalories(), 30);
    } catch (InvalidInputException invalidInputException) {
            fail("shouldn't have caught exception");
        }
    }

    @Test

    public void testAddNullIngredient() {
        try {
            Ingredient i2 = new Ingredient(null, 30);
            assertEquals(i2.getIngredient(), "");
            assertEquals(i2.getCalories(), 30);
        } catch (InvalidInputException invalidInputException) {
            //do nothing
        }

    }

    @Test

    public void testAddNegCalIngredient() {
        try {
            Ingredient i3 = new Ingredient("coffee", -7);
            assertEquals(i3.getIngredient(), "coffee");
            assertEquals(i3.getCalories(), -7);
        } catch (InvalidInputException invalidInputException) {
            //do nothing
        }
    }

    @Test

    public void testZeroCalIngredient() {
        try {
            Ingredient i4 = new Ingredient("apple", 0);
        } catch (InvalidInputException e) {
            fail("shouldn't have caught exception");
        }
    }

    @Test
    public void testBothInvalidInputs() {
        try {
            Ingredient i = new Ingredient(null, -1);
        } catch (InvalidInputException e) {
            //do nothing
        }
    }














}
