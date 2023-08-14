package com.techelevator.dao;

import com.techelevator.model.Review;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcReviewDao implements ReviewDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Review getReview(int userId, int landmarkId) {
        Review review = new Review();
        String sql = "SELECT * FROM reviewed_landmark WHERE user_id = ? AND landmark_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, landmarkId);
        try {
            if (result.next()) {
                review.setUserId(result.getInt("user_id"));
                review.setLandmarkId(result.getInt("landmark_id"));
                review.setReviewed(result.getBoolean("reviewed"));
            }
        } catch (InvalidResultSetAccessException e) {
            e.printStackTrace();
        }
        return review;
    }

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviewed_landmark(user_id, landmark_id, reviewed) VALUES(?,?,?)";
        jdbcTemplate.update(sql, review.getUserId(), review.getLandmarkId(), review.isReviewed());
    }
}
