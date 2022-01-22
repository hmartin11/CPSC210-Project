package model;

// represents a meal entry with ingredients, calories and MealType

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Meal implements Writable {

    private String mealName;
    private int mealCals;
    private List<Ingredient> ingredients;
    private MealType type;

    //add meal throws exception: InvalidInputException
    public Meal(String mealName, MealType type) {
        this.mealName = mealName;
        this.type = type;
        this.mealCals = 0;
        this.ingredients = new ArrayList<>();
    }


    //public int getCalories() {
       // return this.mealCals;
    //}

    // GETTERS:

    public String getMealName() {
        return this.mealName;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public MealType getType() {
        return this.type;
    }

    public int getMealCals() {
        return this.mealCals;
    }

    //MODIFIES: this
    //EFFECTS: adds ingredients with there calories to a meal
    public void addIngredients(Ingredient i) {
        this.ingredients.add(i);


    }

    //EFFECTS: searches for ingredient from list and returns true if found, false if not found
    public Boolean searchIngredients(String ingredient) {
        for (Ingredient i : ingredients) {
            if (i.getIngredient().contains(ingredient)) {
                return true;
            }
        }
        return false;

    }

    //EFFECTS: produces true if a meal contains a certain ingredient, otherwise false

//    public Boolean mealContains(Ingredient ingredient) {
//        if (this.getIngredients().contains(ingredient)) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

    //EFFECTS: returns calories for meal

    public int calcCalories() {
        for (Ingredient i : ingredients) {
            this.mealCals += i.getCalories();
        }
        return this.mealCals;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", mealName);
        json.put("ingredients", ingredientsToJson(ingredients));
        json.put("calories", this.mealCals);
        json.put("type", this.getType());
        return json;

    }

    private JSONArray ingredientsToJson(List<Ingredient> ingredients) {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredients) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
