package persistance;

import model.Ingredient;
import model.Meal;
import model.MealType;
import model.MyMealDiary;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    //CODE SOURCE: this class was inspired by JsonSerializationDemo


    @Test
    void testWritterInvalidFile() {
        try {
            MyMealDiary md = new MyMealDiary();
            JsonWriter wr = new JsonWriter("./data/my\0illegal:fileName.json");
            wr.open();
            fail("IOException was expected");
        } catch  (IOException e) {
            //
        }
    }

    @Test
    void  testWriterEmptyDiary() {
        try {
            MyMealDiary md = new MyMealDiary();
            JsonWriter wr = new JsonWriter("./data/testWriterEmpty.json");
            wr.open();
            wr.write(md);
            wr.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            md = reader.read();
            assertEquals(0, md.getMealDiary().size());
            assertEquals(md.getTotalCals(), 0);
            assertEquals(md.getDailyCalsLeft(), 0);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMyMealDiary() {
        try {
            MyMealDiary md = new MyMealDiary();
            md.setDailyCalsGoal(2000);
            Meal m1 = new Meal("pasta", MealType.DINNER);
            Ingredient i1 = new Ingredient("penne", 200);
            Ingredient i2 = new Ingredient("sauce", 200);
            Ingredient i3 = new Ingredient("parmesan", 100);
            m1.addIngredients(i1);
            m1.addIngredients(i2);
            m1.addIngredients(i3);

            Meal m2 = new Meal("cookie", MealType.SNACK);
            Ingredient i4 = new Ingredient("cookie", 300);
            m2.addIngredients(i4);

            md.addMealToDiary(m1);
            md.addMealToDiary(m2);


            JsonWriter wr = new JsonWriter("./data/testWriterMyMealDiary.json");
            wr.open();
            wr.write(md);
            wr.close();

            JsonReader reader = new JsonReader("./data/testWriterMyMealDiary.json");
            md = reader.read();

            List<Meal> meals = md.getMealDiary();
            List<Ingredient> ingredientList1 = m1.getIngredients();
            List<Ingredient> ingredientList2 = m2.getIngredients();
            assertEquals(meals.size(), 2);
            checkMealDiary("pasta", MealType.DINNER, ingredientList1, m1);
            checkMealDiary("cookie", MealType.SNACK, ingredientList2, m2);
            //assertTrue(meals.get(1).getIngredients().contains(i1));
            assertEquals(md.getDailyCalsLeft(), 1200);
            assertEquals(md.getTotalCals(), 800);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }



}














