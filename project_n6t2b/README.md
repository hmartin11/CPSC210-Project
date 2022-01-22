# *MyMeals*

## Meal Diary Application


Do you struggle to stick to stay motivated to eat healthy? Are you trying to lose weight but find it 
hard to eat the right amount of food? Do you hate math so counting calories is
a struggle? If you relate to any of these questions, MyMeals might be just what you need!

This meal diary app lets you set a daily calorie goal specific to your needs. It then lets you 
add meals with ingredients, and will calculate the calories of the meal. It will subtract each meal
from the daily calorie goal so you know how many calories you have left.

This application interests me because I have used similar apps such as MyFitnessPal and I want to build
my own app like this. I also am interested in the obesity epidemic in North America, and this 
app could help people get on track to start making better food choices.

## User Stories:

- As a user, I want to be able to add meals to my meal diary
- As a user, I want to be able to view the meals in the diary
- As a user, I want to be able to add ingredients to meals
- As a user, I want to be able to set a daily calorie goal
- As a user, I want to be able to calculate daily calories 
- As a user, I want to be able to save my meal diary to file 
- As a user, I want to be able to load my meal diary from file

## Phase 4: task 2: 

Test and design a class in your model package that is robust:
Class: Ingredient
Method: Ingredient constructor throws InvalidInputException

##Phase 4: task 3:

There are a few ways I could refactor the code of my Meal Diary application to improve its design. 
Firstly, there is some duplication in my GUI class. To improve coupling, I would identify code clones and 
pull them into their own methods. One example is the methods: setJLabelFont1(JLabel l) and setJLabelFont2(JLabel l).
The only thing different between these methods is the font, and the font size. To refactor this I would keep
one of the methods, rename it and change the signature to : 

    setJLabelFont(JLabel l, String fontFilePath, Float x)
Then, the following part of the method would use the specified parameters:

    Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFilePath)).deriveFont(x);

The rest of the method stays the same. Doing this allows just one method to be used to set JLabel's with any font and font size. 
I would also split methods up into smaller methods. Currently, the GUI class has some large methods that do 
many things. To refactor I would split the large methods into smaller methods to improve readability.
One example is displayMealDiary(). I would split this into a method that creates the JTextArea and ScrollPane,
and another method that uses a for loop to add meals to the JTextArea. 
Next, to improve the single responsibility principle I would split the GUI into more classes. I would make 
a class to make the panels and set aesthetics, a class to update the GUI based on user input.
Overall, there are multiple ways I could improve the design by refactoring parts of the project. I hope to 
make these changes in the near future, as well as add new features to my project in a way that preserves Object-Oriented
design principles. 





















