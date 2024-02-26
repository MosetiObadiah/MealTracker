package com.mealtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "MealTrackerdb";
    private static final int DB_VERSION = 1;
    private static final String Table_name = "Meals";
    private static final String ID_Column = "ID";
    private static final String Food_1 = "food1";
    private static final String Food_2 = "food2";
    private static final String Food_3 = "food3";
    private static final String Hotel = "hotel";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_name + " ("
                + ID_Column + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Food_1+ " TEXT,"
                + Food_2 + " TEXT,"
                + Food_3 + " TEXT,"
                + Hotel + " TEXT)";

        db.execSQL(query);
    }

    public void addNewFoodRecord(String food1, String food2, String food3, String hotel) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Food_1, food1);
        values.put(Food_2, food2);
        values.put(Food_3, food3);
        values.put(Hotel, hotel);

        // after adding all values we are passing
        // content values to our table.
        db.insert(Table_name, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<FoodModal> readCourses()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM " + Table_name, null);

        // on below line we are creating a new array list.
        ArrayList<FoodModal> courseModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                courseModalArrayList.add(new FoodModal(
                        cursorCourses.getString(1),
                        cursorCourses.getString(4),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }
}
