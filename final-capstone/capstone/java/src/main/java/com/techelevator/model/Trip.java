package com.techelevator.model;

public class Trip {

    private int userId;
    private int itineraryId;

    public Trip(int userId, int itineraryId) {
        this.userId = userId;
        this.itineraryId = itineraryId;
    }

    public Trip() {
    }

    public int getUserId() {
        return userId;
    }


    public int getItineraryId() {
        return itineraryId;
    }

}
