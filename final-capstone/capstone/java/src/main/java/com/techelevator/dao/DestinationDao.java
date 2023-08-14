package com.techelevator.dao;


public interface DestinationDao {
     void addDestination(int itineraryId, int landmarkId);

     void deleteDestination(int itineraryId, int landmarkId);
}
