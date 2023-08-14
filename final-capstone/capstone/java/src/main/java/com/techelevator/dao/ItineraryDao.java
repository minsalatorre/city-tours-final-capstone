package com.techelevator.dao;

import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;

import java.util.List;

public interface ItineraryDao {

    void createNewItinerary(Itinerary newItinerary, String username);

    void deleteItinerary(int id);

    List<Itinerary> getAllItineraries(int userId);

    void updateItinerary(Itinerary newItinerary);

    Itinerary getSpecificItinerary(int itineraryId);

    List<Itinerary> getAllItinerariesByUserName(String userName);

    List<Itinerary> getSharedItineraries(int userid);

    List<Itinerary> getCombinedItineraries(int userId);



}
