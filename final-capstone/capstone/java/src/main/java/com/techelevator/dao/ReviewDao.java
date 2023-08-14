package com.techelevator.dao;

import com.techelevator.model.Review;

public interface ReviewDao {

    Review getReview(int userId, int landmarkId);
    void addReview(Review review);

}
