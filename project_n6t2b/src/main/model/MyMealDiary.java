package model;

//creates

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class MyMealDiary implements Writable {

    private List<Meal> mealDiary;

    private String todaysDate;
    private int dailyCals;
    private int totalCals;



    public MyMealDiary() {

        this.todaysDate = null;
        this.mealDiary = new ArrayList<>();

        this.dailyCals = 0;
        this.totalCals = 0;


    }


    //MODIFIES: THIS
    //EFFECTS: adds meal to diary, update totalCals, updates how much calories are left (dailyCals)
    public void addMealToDiary(Meal m) {
        this.mealDiary.add(m);
        int cals = m.calcCalories();
        this.dailyCals -= cals;
        this.totalCals += cals;

    }


    //MODIFIES: this
    //EFFECTS: sets daily calorie goal for user
    public void setDailyCalsGoal(int calorieGoal) {
        this.dailyCals = calorieGoal;

    }
   //MODIFIES: this
    //EFFECTS: updates todaysDate by user

    public void setTodaysDate(String date) {
        this.todaysDate = date;

    }

    //GETTERS:

    public int getTotalCals() {
        return this.totalCals;
    }

    public List<Meal> getMealDiary() {
        return this.mealDiary;
    }

    public int getDailyCalsLeft() {
        return this.dailyCals;
    }

    public String getDate() {
        return this.todaysDate;
    }

    //CODE SOURCE: toJson and diaryToJson adapted from JsonSerializationDemo

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("mealDiary", diaryToJson(mealDiary));
        json.put("calLeft", getDailyCalsLeft());
        json.put("calTotal", getTotalCals());
        return json;
    }

    private JSONArray diaryToJson(List<Meal> meals) {
        JSONArray jsonArray = new JSONArray();

        for (Meal meal : meals) {
            jsonArray.put(meal.toJson());
        }
        return jsonArray;
    }
}







