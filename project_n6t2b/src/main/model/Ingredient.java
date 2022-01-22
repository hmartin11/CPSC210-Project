package model;

//represents an ingredient with its name and calories

import exceptions.InvalidInputException;
import org.json.JSONObject;

public class Ingredient {

    private String ingredient;
    private int calories;


    //EFFECTS throws exception if user inputs invalid name or calories
    public Ingredient(String name, int calories) throws InvalidInputException {

        if (name == null || calories < 0) {
            throw new InvalidInputException();
        }
        this.ingredient = name;
        this.calories = calories;

    }

    //GETTERS

    public String getIngredient() {
        return this.ingredient;
    }

    public int getCalories() {
        return this.calories;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ingredient", ingredient);
        json.put("cals", getCalories());
        return json;
    }
}
