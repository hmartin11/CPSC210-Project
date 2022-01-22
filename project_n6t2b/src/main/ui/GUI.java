package ui;

import exceptions.InvalidInputException;
import model.Ingredient;
import model.Meal;
import model.MealType;
import model.MyMealDiary;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import persistence.JsonReader;
import persistence.JsonWriter;

public class GUI extends JFrame implements ActionListener {

    private static final int WIDTH = 750;
    private static final int HEIGHT = 550;
    private MyMealDiary diary;
    private Meal meal;
    private JPanel headerPanel;
    private JPanel displayMeals;
    private JPanel welcomePanel;
    private JPanel addMealPanel;
    private JButton button;

    private static final String JSON_STORE = "./data/diary.json";
    //private Object options;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private List<Meal> meals;
    private int cals;
    private int total;
    private JLabel remainingCalsLabel;
    private JLabel totalCalLabel;
    private JLabel vidLabel;
    private JTextField tf1;
    private JTextField tf2;
    private JTextField textField;
    private JComboBox mealTypes;
    Object[] possibleValues = {"Set", "Load"};
    Object[] options = {"Add", "done"};
    String name;
    MealType type;


    //source: code in the GUI class was influenced by the UI Swing Java Tutorials,
    // https://docs.oracle.com/javase/tutorial/uiswing/index.html ,
    // as well as other sites specified below

    //EFFECTS: creates & runs GUI MyMealDiary app
    public GUI() {

        super("MyMealDiary");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().setBackground(Color.WHITE);

        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        init();

        welcomePopUp();
        makeHeader();
        headerPanel.setVisible(true);
        add(welcomePanel);

        JLabel image = new JLabel(new ImageIcon("data/PNG image.png"));
        image.setVisible(true);
        welcomePanel.add(image);

        JButton b1 = new JButton("Add new meal!");
        JButton b2 = new JButton("View meal diary");
        //JButton b3 = new JButton("Save meal plan");
        addButtons(b1, b2);
        setButtonActions(b1, b2);

        welcomePanel.setVisible(true);

        newMealPanel();
        displayMealDiaryPanel();
        savePopup();

    }

    //EFFECTS: initializes meal diary
    private void init() {
        diary = new MyMealDiary();
        meals = new ArrayList<>();
        meal = new Meal(name, type);
        cals = 0;
        total = 0;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

    }

    //MODIFIES: this
    //EFFECTS: creates header panel displaying Total Cals so far
    //          and Cals Left Today
    private JPanel makeHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
        headerPanel.add(totalCalPanel());
        headerPanel.add(calsRemainingPanel());
        add(headerPanel, BorderLayout.PAGE_START);
        return headerPanel;


    }

    //EFFECTS: creates total calories panel
    private JPanel totalCalPanel() {
        JPanel totalCalPanel = new JPanel();

        totalCalPanel.setBackground(new Color(255, 237, 247));
        total = diary.getTotalCals();
        totalCalLabel = new JLabel("Total Calories: " + total);
        setJLabelFont2(totalCalLabel);
        totalCalPanel.add(totalCalLabel);
        totalCalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return totalCalPanel;

    }

    //EFFECTS: creates cal remaining panel
    private JPanel calsRemainingPanel() {
        JPanel remainingCals = new JPanel();
        remainingCals.setBackground(new Color(255, 237, 247));
        cals = diary.getDailyCalsLeft();
        remainingCalsLabel = new JLabel("Calories left today: " + cals);
        setJLabelFont2(remainingCalsLabel);
        remainingCals.add(remainingCalsLabel);
        remainingCals.setBorder(BorderFactory.createLineBorder(Color.black));
        return remainingCals;

    }


    //EFFECTS: adds buttons to welcome panel
    private void addButtons(JButton b1, JButton b2) {
        welcomePanel.add(b1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        welcomePanel.add(b2);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }


    //MODIFIES: this
    //source:  modelled from: https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    //EFFECTS: sets buttons actions
    private void setButtonActions(JButton b1, JButton b2) {
        b1.setActionCommand("Add new meal");
        b1.addActionListener(this);
        b2.setActionCommand("view meal diary");
        b2.addActionListener(this);

    }


    //source: code modelled from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#create
    //MODIFIES: this
    //EFFECTS: shows popup message asking user to set cal goal or load saved file
    private void welcomePopUp() {
        //returns position of option clicked:
        ImageIcon str = new ImageIcon("data/donuticon.jpeg");

        JLabel label1 = makeJLabel("Please set your daily calorie goal \n or load saved diary from file:");

        int popUp = JOptionPane.showOptionDialog(null,
                label1,
                "Welcome to MyMealDiary!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                str, possibleValues, possibleValues[0]);



        JLabel label2 = makeJLabel("Enter Today's Calorie Goal: ");
        if (popUp == 0) {  //set cal goal
            String dailyCals = JOptionPane.showInputDialog(null, label2); //user input
            int c = Integer.parseInt(dailyCals);
            diary.setDailyCalsGoal(c);
            playSound("data/sound2.wav");

        } else if (popUp == 1) { //load json file
            try {
                diary = jsonReader.read();
                displayMealDiaryPanel();
            } catch (Exception e) {
                System.out.println("Unable to read from file" + JSON_STORE);
            }
        }
    }

    //EFFECTS: makes Jlabel and sets font, returns JLabel
    private JLabel makeJLabel(String str) {
        JLabel label = new JLabel(str);
        setJLabelFont2(label);
        return label;
    }

    //EFFECTS: performs actions when buttons are clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Meal")) {

            newMeal();
        } else if (e.getActionCommand().equals("meal")) {
            mealType();
//
        } else if (e.getActionCommand().equals("Add new meal")) {
            viewAddMeal();
        } else if (e.getActionCommand().equals("Back")) {
            if (vidLabel != null) {
                vidLabel.setVisible(false);
            }
            viewWelcomeMenu();
        } else if (e.getActionCommand().equals("view meal diary")) {
            viewMealDiary();
        }
    }


    //EFFECTS: creates JPanel to add new meal, adds action commands for buttons
    private void newMealPanel() {
        addMealPanel = new JPanel();
        addMealPanel.setLayout(new BoxLayout(addMealPanel, BoxLayout.PAGE_AXIS));
        button = new JButton("Add Meal");
        addMealPanel.setBackground(Color.WHITE);

        JLabel meal = new JLabel("Enter Meal Name: ");
        setJLabelFont2(meal);

        textField = new JTextField(5);
        textField.setSize(700, 35);
        textField.setEditable(true);

        String[] mealType = {"Breakfast", "Lunch", "Dinner", "Snack"};
        mealTypes = new JComboBox(mealType);
        mealTypes.setActionCommand("meal");
        mealTypes.addActionListener(this);

        JButton back = new JButton("Back to Menu");
        back.setActionCommand("Back");
        back.addActionListener(this);

        button.setActionCommand("Add Meal");
        button.addActionListener(this);

        JLabel pic = new JLabel(new ImageIcon("data/addmeal.jpeg"));

        addToMealPanel(pic, meal, textField);
        addMealPanel.add(mealTypes);
        addButtonsToPanel(button, back);

    }

    //EFFECTS: adds Jlabels and TextFields to addMealPanel
    private void addToMealPanel(JLabel l1, JLabel l2, JTextField t1) {

        addMealPanel.add(l1);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMealPanel.add(l2);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMealPanel.add(t1);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    //EFFECTS: adds buttons to panel, sets alignment
    private void addButtonsToPanel(JButton b1, JButton b2) {
        addMealPanel.add(b1);
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMealPanel.add(b2);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);

    }


    //source: dialog code modelled from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#create
    //MODIFIES: this
    //EFFECTS: creates popup for user to add ingredients to a meal & updates meal diary adding that meal
    private void addIngredient(Meal m) {
        boolean adding = true;
        //ImageIcon icon2 = new ImageIcon("data/4001959-middle.png");
        while (adding) {

            int popUp = addIngredientPopup();
//            int popUp = JOptionPane.showOptionDialog(null,
//                    makeJLabel("Please add a new ingredient or click done: "), "Add Ingredients!",
//                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon2, options, options[0]);

            if (popUp == 0) {
                addGif();
                String i = JOptionPane.showInputDialog(null, makeJLabel("Enter Ingredient")); //user input
                String cal = JOptionPane.showInputDialog(null, makeJLabel("Enter Calories"));

                int c = Integer.parseInt(cal);
                try {
                    Ingredient i1 = new Ingredient(i, c);
                    m.addIngredients(i1);
                } catch (InvalidInputException invalidInputException) {
                    System.out.println("Invalid Ingredient Input");
                }
            }

            if (popUp == 1) {
                playSound("data/sound1.wav");
                adding = false;

            }
        }
        this.diary.addMealToDiary(m);
        updateMealDiary();
//
    }

    //EFFECTS: makes popup option dialog for adding ingredients to meal
    private int addIngredientPopup() {
        ImageIcon icon2 = new ImageIcon("data/4001959-middle.png");
        int popUp = JOptionPane.showOptionDialog(null,
                makeJLabel("Please add a new ingredient or click done: "), "Add Ingredients!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon2, options, options[0]);
        return popUp;

    }

    //MODIFIES: this
    //EFFECTS: updates meal diary
    private void updateMealDiary() {
        meals = diary.getMealDiary();
        updateCalsLeft();
        updateTotalCals();
    }



    //MODIFIES: this
    //EFFECTS: creates new meal, adds ingredients to it
    private void newMeal() {
        String name = textField.getText();
        MealType t = mealType();

        Meal m = new Meal(name, t);
        addIngredient(m);
        textField.setText("");
    }

    //MODIFIES: this
    //EFFECTS: updates total calories
    private void updateTotalCals() {
        total = diary.getTotalCals();
        totalCalLabel.setText("Total Calories: " + total);
    }

    //MODIFIES: this
    //EFFECTS: updates calories left
    private void updateCalsLeft() {
        cals = diary.getDailyCalsLeft();
        remainingCalsLabel.setText("calories left today: " + cals);

    }

    //MODIFIES: this
    //EFFECTS: returns MealType based on user's selection
    private MealType mealType() {
        String selected = (String) mealTypes.getSelectedItem();
        MealType type = MealType.BREAKFAST;
        if (selected == "Breakfast") {
            type = MealType.BREAKFAST;
        } else if (selected == "Lunch") {
            type = MealType.LUNCH;
        } else if (selected == "Dinner") {
            type = MealType.DINNER;
        } else if (selected == "Snack") {
            type = MealType.SNACK;
        }
        return type;

    }

    //EFFECTS: creates JPanel to display meal diary
    private JPanel displayMealDiaryPanel() {

        displayMeals = new JPanel();
        displayMeals.setLayout(new BoxLayout(displayMeals, BoxLayout.PAGE_AXIS));


        this.meals = diary.getMealDiary();
        JLabel welcome = new JLabel("Today's Meal Diary: ");
        welcome.setIcon(new ImageIcon("data/Planner-Diary-Icons-Graphics-14853952-1.png"));

        setJLabelFont1(welcome);
        displayMeals.add(welcome);
        Color c = new Color(255, 237, 247);
        displayMeals.setBackground(c);

        displayMealDiary();

        JButton b = new JButton("Back to Menu");
        b.setActionCommand("Back");
        b.addActionListener(this);
        displayMeals.add(b);

        return displayMeals;
    }

    //MODIFIES: this
    //EFFECTS: creates display area for meal diary
    private void displayMealDiary() {
        JTextArea display = new JTextArea(60, 60);
        displayMeals.add(display);
        JScrollPane scroll = new JScrollPane(display);
        displayMeals.add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        display.setEditable(false);
        display.setVisible(true);
        display.setText("");
        setTxtAreaFont(display);

        for (Meal meal : meals) {
            String mealName = meal.getMealName();
            MealType mealType = meal.getType();
            String type = mealType.toString();
            int cal = meal.getMealCals();
            String cals = Integer.toString(cal);

            display.append("Name: " + mealName + "\n");
            display.append("Meal Type: " + type + "\n");
            display.append("Calories: " + cals + "\n");
            display.append("\n");

        }
    }


    //MODIFIES: this
    //EFFECTS: adds addMealPanel to frame, turns off visibility of other panels
    private void viewAddMeal() {
        add(addMealPanel);
        addMealPanel.setVisible(true);
        welcomePanel.setVisible(false);
        displayMeals.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: turns off visibility for addMealPanel and displayMeals
    private void viewWelcomeMenu() {
        addMealPanel.setVisible(false);
        welcomePanel.setVisible(true);
        displayMeals.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: adds displayMeals Panel to frame, turns off visibility of other panels
    private void viewMealDiary() {
        //displayMealDiary();
        add(displayMealDiaryPanel());
        addMealPanel.setVisible(false);
        welcomePanel.setVisible(false);
        displayMeals.setVisible(true);
    }


    //EFFECTS: creates popup window when user exits app to prompt them to save diary
    private void savePopup() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int save = JOptionPane.showConfirmDialog(null,
                        makeJLabel("Would you like to save your meal diary before exiting?"), "Save File",
                        JOptionPane.YES_NO_OPTION);
                if (save == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(diary);
                        jsonWriter.close();
                    } catch (FileNotFoundException f) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    dispose();
                }

            }
        });

    }

    //EFFECTS: creates gif jlabel
    private void addGif() {
        Icon icon = new ImageIcon("data/giphy.gif");
        vidLabel = new JLabel(icon);
        addMealPanel.add(vidLabel);
        vidLabel.setVisible(true);
        vidLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //vidLabel.setHorizontalAlignment(JLabel.CENTER);
    }


    //source: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    //EFFECTS: plays sound
    private void playSound(String soundName) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound");
            ex.printStackTrace();
        }


    }

    //source: following 3 methods were modelled from:
    // https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java/5652385#5652385

    //EFFECTS: adds & sets custom font for Jlabel
    private void setJLabelFont1(JLabel l) {
        try {
            //create font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("data/CHERL___.TTF")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont);
            l.setFont(customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

    }

    //EFFECTS: adds & sets custom font for Jlabel
    private void setJLabelFont2(JLabel l) {
        try {
            //create font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("data/Love.ttf")).deriveFont(22f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont);
            l.setFont(customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

    }

    //EFFECTS: adds & sets custom font for Jtextarea
    private void setTxtAreaFont(JTextArea txt) {
        try {
            //create font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("data/Love.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont);
            txt.setFont(customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

    }


}
