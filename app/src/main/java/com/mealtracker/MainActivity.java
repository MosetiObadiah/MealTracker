package com.mealtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView email;
    TextView password;
    TextView selectDisplay1;
    TextView selectDisplay2;
    Button food1Btn, food2Btn, food3Btn, food4Btn, food5Btn, otherFoodBtn;
    Button[] foodsButtonGroup;
    Button midCityBtn, lakersBtn, winnersBtn, messBtn, cookingBtn, otherHotelBtn;
    Button[] hotelsBtnGroup;
    Button googleInBtn ;
    Button appleInBtn;
    int timeOfDay;
    ArrayList<String> selectedFoods = new ArrayList<>();
    ArrayList<String> selectedHotels = new ArrayList<>();

    // creating variables for our edittext, button and dbhandler
    private String food1Name, food2Name, food3Name, hotelName;
    private Button saveToDBButton;
    private DBHandler dbHandler;
    Button goToHomeBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        googleInBtn = findViewById(R.id.googleSignIn);
        appleInBtn = findViewById(R.id.appleSignIn);
    }

    @SuppressLint("MissingInflatedId")
    public void login(View view) {

        email = findViewById(R.id.emailView);
        password = findViewById(R.id.passwordView);
        googleInBtn = findViewById(R.id.googleSignIn);
        appleInBtn = findViewById(R.id.appleSignIn);

        setContentView(R.layout.homepage);
        autoMealTimeChanger();
    }

    @SuppressLint("SetTextI18n")
    private void autoMealTimeChanger() {
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView timeView = findViewById(R.id.timeTextView);
        Calendar calendar = Calendar.getInstance();
        timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        /*debug
        Log.d("time", "time is "+ timeOfDay);
        */

        selectDisplay1 = findViewById(R.id.selectiondisplayView1);
        selectDisplay2 = findViewById(R.id.selectiondisplayView2);

        food1Btn = findViewById(R.id.food1Btn);
        food2Btn = findViewById(R.id.food2Btn);
        food3Btn = findViewById(R.id.food3Btn);
        food4Btn = findViewById(R.id.food4Btn);
        food5Btn = findViewById(R.id.food5Btn);
        otherFoodBtn = findViewById(R.id.otherFoodBtn);

        midCityBtn = findViewById(R.id.MidCityBtn);
        lakersBtn = findViewById(R.id.lakersBtn);
        winnersBtn = findViewById(R.id.winnersBtn);
        messBtn = findViewById(R.id.messBtn);
        cookingBtn = findViewById(R.id.cookedBtn);
        otherHotelBtn = findViewById(R.id.otherPlaceBtn);

        saveToDBButton = (Button) findViewById(R.id.saveBtnView);

        hotelsBtnGroup = new Button[]{midCityBtn, lakersBtn, winnersBtn, messBtn, cookingBtn, otherHotelBtn};

        String[] morningMeals = {"Tea", "mandazi", "chapati", "coffee", "Mayai"};
        String[] lunchMeals = {"Rice", "Beans", morningMeals[2], "Waru", "Ugali", morningMeals[4]};

        foodsButtonGroup = new Button[]{food1Btn, food2Btn, food3Btn, food4Btn, food5Btn};

        if (timeOfDay < 10) {
            timeView.setText("BreakFast");
            for (int i = 0; i < foodsButtonGroup.length; i++) {
                foodsButtonGroup[i].setText(morningMeals[i]);
            }

        } else if (timeOfDay > 16) {
            timeView.setText("Supper");
            for (int i = 0; i < foodsButtonGroup.length; i++) {
                foodsButtonGroup[i].setText(lunchMeals[i]);
            }
        } else {
            timeView.setText("Lunch");
            for (int i = 0; i < foodsButtonGroup.length; i++) {
                foodsButtonGroup[i].setText(lunchMeals[i]);
            }
        }

        //TODO timer is supposed to refresh time check after 1 hour to ensure accurate message

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);

        // below line is to add on click listener for our add course button.
        saveToDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check the number of selected foods and hotels
                if (selectedFoods.size() != 3) {
                    Toast.makeText(MainActivity.this, "Please select exactly 3 foods.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedHotels.size() != 1) {
                    Toast.makeText(MainActivity.this, "Please select exactly 1 hotel.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // below lines are to get data from all edit food buttons selected and hotel selected fields.
                food1Name = selectedFoods.get(0);
                food2Name = selectedFoods.get(1);
                food3Name = selectedFoods.get(2);
                hotelName = selectedHotels.get(0);

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewFoodRecord(food1Name, food2Name, food3Name, hotelName);

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Meal Record has been added.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openRecords(View view) {
        setContentView(R.layout.recordspage);
        goToHomeBtn = findViewById(R.id.goHome);
    }

    @SuppressLint("SetTextI18n")
    public void notYetImplemented(View view) {
        ((Button)view).setText("Not Yet Implemented");
        view.setActivated(false);
    }

    @SuppressLint("SetTextI18n")
    public void getFood(View view) {
        if (selectedFoods.size() < 3) {
            selectedFoods.add(((Button) view).getText().toString());
            ((Button) view).setEnabled(false);
            updateSelectionDisplay();
        } else {
            Toast.makeText(MainActivity.this, "You can only select up to 3 foods.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getHotel(View view) {
        // Clear the previously selected hotel
        for (Button hotelButton : hotelsBtnGroup) {
            hotelButton.setEnabled(true);
        }

        selectedHotels.clear();

        selectedHotels.add(((Button) view).getText().toString());
        ((Button) view).setEnabled(false);
        updateSelectionDisplay();
    }

    private void updateSelectionDisplay() {
        StringBuilder foodsText = new StringBuilder();
        for (String food : selectedFoods) {
            foodsText.append(food).append(", ");
        }
        // Removes the trailing comma and space
        if (foodsText.length() > 0) {
            foodsText.setLength(foodsText.length() - 2);
        }
        selectDisplay1.setText(foodsText.toString());

        StringBuilder hotelsText = new StringBuilder();
        for (String hotel : selectedHotels) {
            hotelsText.append(hotel).append(", ");
        }
        // Removes the trailing comma and space
        if (hotelsText.length() > 0) {
            hotelsText.setLength(hotelsText.length() - 2);
        }
        selectDisplay2.setText(hotelsText.toString());
    }
}