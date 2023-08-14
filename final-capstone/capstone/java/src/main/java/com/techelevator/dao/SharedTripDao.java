package com.techelevator.dao;

import com.techelevator.model.SharedTrip;

public interface SharedTripDao {
    void addSharedTrip(SharedTrip sharedTrip);
    SharedTrip getShareTrip(int sharedTripId);
}
