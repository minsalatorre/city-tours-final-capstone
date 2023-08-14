package com.techelevator.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLandmarkDao implements LandmarkDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcLandmarkDao(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Landmark> getAllLandmarks() {
        List<Landmark> listOfLandmarks = new ArrayList<>();
        String sql = "SELECT * FROM landmarks ORDER BY landmark_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        try{
            while (results.next()) {
                Landmark landmark = mapRowLandmark(results);
                listOfLandmarks.add(landmark);
            }
        }
        catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException();
        }

        return listOfLandmarks;
    }

    @Override
    public List<Landmark> getLandmarksOnItinerary(int itineraryId) {
        List<Landmark> resultList = new ArrayList<>();
        String sql = "SELECT * FROM landmarks as l" +
                " JOIN destinations as d ON l.landmark_id = d.landmark_id" +
                " WHERE d.itinerary_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itineraryId);
            while (results.next()) {
                resultList.add(mapRowLandmark(results));
            }
            return resultList;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public Landmark getSpecificLandmarkById(int id) {
        Landmark landmark = new Landmark();
        String sql = "SELECT * FROM landmarks WHERE landmark_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        try {
            if (results.next()) {
                landmark = mapRowLandmark(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException();
        } return landmark;
    }

    @Override
    public int addNewLandmark(Landmark newLandmark) {
        String sql = "INSERT INTO landmarks (landmark_name, landmark_photo, landmark_description, landmark_address, landmark_hours_of_operation, landmark_venue_type) VALUES (?,?,?,?,?,?) RETURNING landmark_id";
        try{
            int newLandmarkId = jdbcTemplate.queryForObject(sql, int.class, newLandmark.getLandmarkName(), newLandmark.getLandmarkPhoto(), newLandmark.getLandmarkDescription(), newLandmark.getLandmarkAddress(), newLandmark.getLandmarkHoursOfOperation(), newLandmark.getLandmarkVenueType());
            newLandmark.setLandmarkId(newLandmarkId);
            return newLandmarkId;
        }
        catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void addLikeToLandmark(int landmarkId) {
        String addLike = "UPDATE landmarks" +
                " SET landmark_like_count = landmark_like_count + 1" +
                " WHERE landmark_id = ?";
        jdbcTemplate.update(addLike, landmarkId);
    }

    @Override
    public int getLikesFromLandmark(int landmarkId) {
        Landmark landmark = new Landmark();
        String getLikesSql = "SELECT landmark_like_count FROM landmarks WHERE landmark_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getLikesSql, landmarkId);
        if(results.next()){
            landmark = mapRowLandmark(results);
        }
        return landmark.getLandmarkLikeCount();
    }

    @Override
    public int getDislikesFromLandmark(int landmarkId) {
        String getDislikesSql = "SELECT landmark_dislike_count FROM landmarks WHERE landmark_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getDislikesSql, landmarkId);
        int likes = results.getInt("landmark_dislike_count");
        return likes;
    }

    @Override
    public void addDislikesToLandmark(int landmarkId) {
        String addDislike = "UPDATE landmarks" +
                " SET landmark_dislike_count = landmark_dislike_count + 1" +
                " WHERE landmark_id = ?";
        jdbcTemplate.update(addDislike, landmarkId);
    }


    private Landmark mapRowLandmark(SqlRowSet rs){
        Landmark landmark = new Landmark();
        landmark.setLandmarkId(rs.getInt( "landmark_id"));
        landmark.setLandmarkName(rs.getString("landmark_name"));
        landmark.setLandmarkPhoto(rs.getString("landmark_photo"));
        landmark.setLandmarkDescription(rs.getString("landmark_description"));
        landmark.setLandmarkAddress(rs.getString("landmark_address"));
        landmark.setLandmarkHoursOfOperation(rs.getString("landmark_hours_of_operation"));
        landmark.setLandmarkVenueType(rs.getString("landmark_venue_type"));
        landmark.setLandmarkLikeCount(rs.getInt("landmark_like_count"));
        landmark.setLandmarkDislikeCount(rs.getInt("landmark_dislike_count"));
        return landmark;
    }
}
