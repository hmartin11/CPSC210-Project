package persistance;

import model.Ingredient;
import model.Meal;
import model.MealType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {

    //CODE SOURCE: JsonSerializationDemo

    protected void checkMealDiary(String name, MealType type, List<Ingredient> ingredients, Meal meal) {

        assertEquals(name, meal.getMealName());
        assertEquals(type, meal.getType());
        assertEquals(ingredients, meal.getIngredients());




    }
}
