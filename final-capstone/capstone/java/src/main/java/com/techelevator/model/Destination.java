package com.techelevator.model;

public class Destination {

    private int itineraryId;
    private int landmarkId;

    public Destination(int itineraryId, int landmarkId) {
        this.itineraryId = itineraryId;
        this.landmarkId = landmarkId;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public int getLandmarkId() {
        return landmarkId;
    }
}
