package com.techelevator.model;

public class Review {

    private int userId;
    private int landmarkId;
    private boolean reviewed;

    public Review() {
    }

    public Review(int userId, int landmarkId, boolean reviewed) {
        this.userId = userId;
        this.landmarkId = landmarkId;
        this.reviewed = reviewed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLandmarkId() {
        return landmarkId;
    }

    public void setLandmarkId(int landmarkId) {
        this.landmarkId = landmarkId;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
