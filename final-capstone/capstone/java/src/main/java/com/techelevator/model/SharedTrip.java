package com.techelevator.model;

public class SharedTrip {
    private int tripId;
    private int user1Id;
    private int user2Id;

    public SharedTrip(int tripId, int user1Id, int user2Id) {
        this.tripId = tripId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public SharedTrip() {
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }
}
