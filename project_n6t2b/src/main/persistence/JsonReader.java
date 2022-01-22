package persistence;

import exceptions.InvalidInputException;
import model.Ingredient;
import model.Meal;
import model.MealType;
import model.MyMealDiary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {

    //CODE SOURCE: JsonReader has been taken & adapted from JsonSerializationDemo

    private String source;
    //private MealType mealType;
    //private Ingredient ingredient;
   // private int cals;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads mealdiary from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyMealDiary read() throws IOException, InvalidInputException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMyMealDiary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //parses MyMealDiary from JSON object and returns it
    private MyMealDiary parseMyMealDiary(JSONObject jsonObject) throws InvalidInputException {
        MyMealDiary md = new MyMealDiary();
        int cal = jsonObject.getInt("calLeft");
        int total = jsonObject.getInt("calTotal");
        int goal = cal + total;
        md.setDailyCalsGoal(goal);
        addMeals(md, jsonObject);
        return md;

    }

    //MODIFIES: md
    //EFFECTS: parses meals from JSON object and adds them to mealdiary
    private void addMeals(MyMealDiary md, JSONObject jsonObject) throws InvalidInputException {
        JSONArray jsonArray = jsonObject.getJSONArray("mealDiary");

        for (Object json : jsonArray) {
            JSONObject nextMeal = (JSONObject) json;
            Meal meal = addMeal(nextMeal);
            md.addMealToDiary(meal);
        }
    }

    //MODIFIES: this
    //EFFECTS: parse meal from jsonObject, adds ingredients, and returns it
    private Meal addMeal(JSONObject jsonObject) throws InvalidInputException {
        String name = jsonObject.getString("name");
        MealType type = MealType.valueOf(jsonObject.getString("type"));
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");

        Meal meal = new Meal(name, type);

        for (int i = 0; i < ingredients.length(); i++) {
            int cals = ingredients.getJSONObject(i).getInt("cals");
            String i1 = ingredients.getJSONObject(i).getString("ingredient");
            Ingredient ingredient = new Ingredient(i1, cals);
            meal.addIngredients(ingredient);
        }
        return meal;

    }

//    private int addCalGoal(MyMealDiary md, JSONObject jsonObject) {
//        md.getDailyCalsLeft()
//
//    }




}
