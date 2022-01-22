package ui;

import exceptions.InvalidInputException;
import model.Ingredient;
import model.Meal;
import model.MealType;
import model.MyMealDiary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.util.List;

import static model.MealType.*;

public class MealDiaryApp {

    private static final String JSON_STORE = "./data/diary.json";
    private List<Meal> mealDiary;
    //private MealType mealType;
    private MyMealDiary myDiary;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the Meal Diary App
    public MealDiaryApp() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);

        myDiary = new MyMealDiary();
        mealDiary = myDiary.getMealDiary();


        runMealDiary();
    }


    //code source: TellerApp

    //MODIFIES: this
    //EFFECTS: processes user input
    public void runMealDiary() {
        boolean keepGoing = true;
        String command = null;

        initDiary();

        //System.out.println("Enter today's calorie goal or 0 when done");
        //doDailyCalGoal();
        //System.out.println("Enter today's date and then type \"yes\"");
        //doDate();
        displayWelcomeMenu();
        command = input.next();
        command = command.toLowerCase();
        welcomeToMyMealDiary(command);



        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processUserCommand(command);
            }
        }

        System.out.println("\nGoodbye!");


    }

    //MODIFIES: this
    //processes user commands for welcome menu
    private void welcomeToMyMealDiary(String command) {
        if (command.equals("c")) {
            doDailyCalGoal();
        } else if (command.equals("o")) {
            loadMealDiary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: displays welcome menu to user
    private void displayWelcomeMenu() {
        System.out.println("\nWelcome to MyMealDiary!");
        System.out.println("\nPlease choose from:");
        System.out.println("\tc -> New Meal Diary: set daily calorie goal");
        System.out.println("\to -> Load Meal Diary from file");

    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processUserCommand(String command) {
        if (command.equals("a")) {
            doAddNewMeal();
        } else if (command.equals("t")) {
            doSeeTotalCal();
        } else if (command.equals("vc")) {
            doGetCalRemaining();
        } else if (command.equals("m")) {
            displayDiary();
        } else if (command.equals("s")) {
            saveMealDiary();
        //} else if (command.equals("o")) {
            //loadMealDiary();
        //} else if (command.equals("c")) {
           // doDailyCalGoal();
        } else {
            System.out.println("Selection not valid...");
        }

    }


    //MODIFIES: this
    //EFFECTS: initializes meal diary
    private void initDiary() {
//        myDiary = new MyMealDiary();
//        mealDiary = myDiary.getMealDiary();
//
//        input = new Scanner(System.in);

    }



    //EFFECTS: displays menu to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
       // System.out.println("\tc -> set daily calorie goal");
        System.out.println("\ta -> add new meal");
        System.out.println("\tt -> view total calories consumed so far");
        System.out.println("\tvc -> view calories left");
        System.out.println("\tm -> display meal diary");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save today's meal plan to file");
       // System.out.println("\to -> load meal plan from file ");

    }


    //MODIFIES: this
    //EFFECTS: adds a newly created meal to the meal diary
    private void doAddNewMeal() {
        System.out.println("Enter meal name");
        String mealName = input.next();
        MealType mealType = selectMealType();

        Meal meal = new Meal(mealName, mealType);
        System.out.println("Add ingredients to meal ");
        addIngredients(meal);
        myDiary.addMealToDiary(meal);


    }

    //REQUIRES: meal name has no spaces
    //MODIFIES: this
    //EFFECTS: selects meal type
    private MealType selectMealType() {
        System.out.println("\nSelect Meal Type");
        System.out.println("\tb -> Breakfast");
        System.out.println("\tl -> Lunch");
        System.out.println("\td -> Dinner");
        System.out.println("\ts -> Snack");
        MealType m = null;
        String type = input.next();

        if (type.equals("b")) {
            m = BREAKFAST;
        } else if (type.equals("l")) {
            m = LUNCH;
        } else if (type.equals("d")) {
            m = DINNER;
        } else if (type.equals("s")) {
            m = SNACK;
        }
        return m;

    }

    //REQUIRES: user enter ingredient as single word (no spaces)
    //MODIFIES: this
    //EFFECTS: adds ingredients to a meal
    private void addIngredients(Meal meal) {
        boolean add = true;
        while (add) {
            String i = input.next();
            System.out.println("enter calories");
            int c = input.nextInt();
            if (i.equalsIgnoreCase("ok")) {
                add = false;
            } else if (c == 0) {
                add = false;
            } else {
                try {
                    Ingredient ingredient = new Ingredient(i, c);
                    meal.addIngredients(ingredient);
                } catch (InvalidInputException e) {
                    System.out.println("Invalid Ingredient");
                }
                System.out.println("add more ingredients or type \"ok\", press enter, and type 0 for calories ");
            }

        }
    }




    //MODIFIES: THIS
    //EFFECTS: sets daily calorie goal
//    private void doDailyCalGoal() {
//        boolean set = true;
//
//        while (set) {
//            int cal = input.nextInt();
//            if (cal == 0) {
//                set = false;
//            } else {
//                System.out.println("Enter today's calorie goal or 0 when done");
//                myDiary.setDailyCalsGoal(cal);
//            }
//        }
//    }
    //REQUIRES: integer value
    //MODIFIES: THIS
    //EFFECTS: sets daily calorie goal
    private void doDailyCalGoal() {
        System.out.println("Hello! Enter today's calorie goal");
        int cal = input.nextInt();
        myDiary.setDailyCalsGoal(cal);

    }



    //MODIFIES: this
    //EFFECTS: sets todays date
//    private void doDate() {
//        boolean set = true;
//
//        while (set) {
//            String date = input.next();
//            if (date.equalsIgnoreCase("yes")) {
//                set = false;
//            } else {
//                System.out.println("Enter today's date and then type \"yes\"");
//                myDiary.setTodaysDate(date);
//            }
//
//        }
//    }
    //REQUIRES: no spaces when user is entering data
    //MODIFIES: this
    //EFFECTS: sets todays date
    private void doDate() {
        System.out.println("Enter today's date (no spaces)");
        String date = input.next();
        myDiary.setTodaysDate(date);

    }




    //EFFECTS: displays calories remaining
    private void doGetCalRemaining() {
        int cal = myDiary.getDailyCalsLeft();
        System.out.println("You have " + cal + " calories remaining today");
    }


    //EFFECTS: displays total calories consumed
    private void doSeeTotalCal() {
        int cal = myDiary.getTotalCals();
        System.out.println("You have eaten " + cal + " calories so far");
    }

    //REQUIRES: user to have added a meal to the diary
    //EFFECTS: displays meal diary
    private void displayDiary() {
        System.out.println("Today's meal diary: ");
        System.out.println("---------------------");
        int total = myDiary.getTotalCals();
        int left = myDiary.getDailyCalsLeft();
        System.out.println("Total Calories Consumed: " + total);
        System.out.println("You have: " + left + " calories left today!");
        System.out.println("---------------------");


        for (Meal meal: mealDiary) {
            String mealName = meal.getMealName();
            MealType mealType = meal.getType();
            int cal = meal.getMealCals();
            //cal = cal / mealDiary.size();
            System.out.println("Meal: " + mealName);
            System.out.println("Calories: " + cal);
            System.out.println("Type: " + mealType);
            System.out.println("---------------------");
        }

    }

//    private void printMeal(Meal m) {
//        String mealName = m.getMealName();
//        double cal = m.getMealCals();
//        cal = cal / 2;
//
//        System.out.println("Meal: " + mealName);
//        System.out.println("Calories: " + cal);
//        System.out.println("---------------------");
//    }

    //EFFECTS: saves the workroom to file
    private void saveMealDiary() {
        try {
            jsonWriter.open();
            jsonWriter.write(myDiary);
            jsonWriter.close();
            System.out.println("Saved " + myDiary + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads workroom from file
    private void loadMealDiary() {
        try {
            myDiary = jsonReader.read();
            mealDiary = myDiary.getMealDiary();
            System.out.println("Loaded " + myDiary + " from " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }



}
