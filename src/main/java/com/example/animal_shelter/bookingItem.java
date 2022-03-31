package com.example.animal_shelter;

public class bookingItem {

    //region [Getter & Setters]

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public String getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(String weekAmount) {
        this.weekAmount = weekAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //endregion

    //region [Variables]

    private String animalName = null;
    private String weekStart = null;
    private String weekAmount = null;
    private String location = null;

    //endregion

    //region [Constructors]

    public bookingItem() {
    }

    public bookingItem(String animalName, String weekStart, String weekAmount, String location) {
        this.animalName = animalName;
        this.weekStart = weekStart;
        this.weekAmount = weekAmount;
        this.location = location;
    }

    //endregion

}
