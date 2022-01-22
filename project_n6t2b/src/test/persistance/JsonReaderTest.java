package persistance;

import exceptions.InvalidInputException;
import model.Ingredient;
import model.Meal;
import model.MealType;
import model.MyMealDiary;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    //CODE SOURCE: JsonReaderTest Class adapted from JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MyMealDiary md = reader.read();
            fail("IOException expected");
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDiary() {
        JsonReader r = new JsonReader("./data/testReaderEmpty");
        try {
            MyMealDiary md = r.read();
            assertEquals(md.getTotalCals(), 0);
            assertEquals(md.getDailyCalsLeft(), 0);
            assertTrue(md.getMealDiary().isEmpty());
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMyMealDiary() {
        JsonReader r = new JsonReader("./data/testReaderMyMealDiary.json");

        try {
            MyMealDiary md = r.read();
            assertEquals(md.getMealDiary().size(), 2);
            Meal m = md.getMealDiary().get(1);
            Meal m1 = md.getMealDiary().get(0);
            List<Ingredient> i1 = m.getIngredients();
            List<Ingredient> i2 = m1.getIngredients();
            assertEquals(md.getDailyCalsLeft(), 1340);
            assertEquals(md.getTotalCals(), 660);
            checkMealDiary("bagel", MealType.BREAKFAST, i1, m);
            checkMealDiary("eggs", MealType.BREAKFAST, i2, m1);
            assertEquals(i1.get(0).getIngredient(), "bagel");
            assertEquals(i2.get(1).getIngredient(), "butter");
            assertEquals(i1.get(0).getCalories(), 300);

        } catch (Exception e) {
            fail("Couldn't read from file");
        }

    }


}
