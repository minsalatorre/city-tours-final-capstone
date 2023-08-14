package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Itinerary {


    private int itineraryId;
//    private int userId;
//
//    private int landmarkId;
    private String itineraryName;
    private LocalDate itineraryDate;
    private String itineraryStartingPoint;

    public Itinerary() {
    }

    public Itinerary(int itineraryId, String itineraryName, LocalDate itineraryDate, String itineraryStartingPoint) {
        this.itineraryId = itineraryId;
        this.itineraryName = itineraryName;
        this.itineraryDate = itineraryDate;
        this.itineraryStartingPoint = itineraryStartingPoint;
    }

    public Itinerary(String itineraryName, LocalDate itineraryDate, String itineraryStartingPoint) {
        this.itineraryName = itineraryName;
        this.itineraryDate = itineraryDate;
        this.itineraryStartingPoint = itineraryStartingPoint;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getItineraryName() {
        return itineraryName;
    }

    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public LocalDate getItineraryDate() {
        return itineraryDate;
    }

    public void setItineraryDate(LocalDate itineraryDate) {
        this.itineraryDate = itineraryDate;
    }

    public String getItineraryStartingPoint() {
        return itineraryStartingPoint;
    }

    public void setItineraryStartingPoint(String itineraryStartingPoint) {
        this.itineraryStartingPoint = itineraryStartingPoint;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "itineraryId=" + itineraryId +
                ", itineraryName='" + itineraryName + '\'' +
                ", itineraryDate=" + itineraryDate +
                ", itineraryStartingPoint='" + itineraryStartingPoint + '\'' +
                '}';
    }
}
