package com.techelevator.dao;

import com.techelevator.model.Landmark;

import java.util.List;

public interface LandmarkDao {

    List<Landmark> getAllLandmarks();
    List<Landmark> getLandmarksOnItinerary(int itineraryId);
    Landmark getSpecificLandmarkById(int id);
    int addNewLandmark(Landmark newLandmark);
    void addLikeToLandmark(int landmarkId);
    int getLikesFromLandmark(int landmarkId);

    int getDislikesFromLandmark(int landmarkId);

    void addDislikesToLandmark(int landmarkId);

}
