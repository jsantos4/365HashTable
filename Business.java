package yelpReviewHashing;

import java.util.ArrayList;

public class Business {
    private String id;
    private String name;
    private String city;
    private String state;
    private Float stars;
    public int similarCategories;
    ArrayList<String> categories;


    Business(String id, String name, String city, String state, Float stars) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.stars = stars;
        categories = new ArrayList<String>();
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getCity() {
        return city;
    }

    private String getState() {
        return state;
    }

    float getStars() {
        return stars;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String toString() {
        return getName() + ", " + getCity() + ", " + getState() + ", " + getStars() + " stars";
    }

}

