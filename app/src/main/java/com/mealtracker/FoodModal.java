package com.mealtracker;

public class FoodModal {

    // variables for our food and Hotel
    private String food1;
    private String food2;
    private String food3;
    private String hotel;
    private int id;

    // creating getter and setter methods
    public String getFood1() { return food1; }

    public void setFood1(String food1)
    {
        this.food1 = food1;
    }

    public String getFood2()
    {
        return food2;
    }

    public void setFood2(String food2)
    {
        this.food2 = food2;
    }

    public String getFood3() { return food3; }

    public void setFood3(String food3)
    {
        this.food3 = food3;
    }

    public String getHotel()
    {
        return hotel;
    }

    public void setHotel(String hotel)
    {
        this.hotel = hotel;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    // constructor
    public FoodModal(String food1,
                       String food2,
                       String food3,
                       String hotel)
    {
        this.food1 = food1;
        this.food2 = food2;
        this.food3 = food3;
        this.hotel = hotel;
    }
}
