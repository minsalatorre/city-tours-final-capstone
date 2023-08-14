package com.techelevator.dao;

import com.techelevator.model.Itinerary;

import java.util.List;

public interface TripDao {
    // add a user id and an itinerary id to the table to create a new 'trip'

    void addTrip(int userId, int itineraryId);

}
