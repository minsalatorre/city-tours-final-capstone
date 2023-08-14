package com.techelevator.dao;

import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;
import com.techelevator.model.Trip;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcItineraryDao implements ItineraryDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcItineraryDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getUserIdByUsername(String username) {
        int userId = 0;
        String sql = "SELECT user_id FROM users WHERE username = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        if (result.next()) {
            userId = result.getInt("user_id");
        } return userId;
    }

    @Override
    public void createNewItinerary(Itinerary newItinerary, String username) {
        String sql = "INSERT INTO itinerary (itinerary_name, itinerary_date, itinerary_starting_point) VALUES (?,?,?) RETURNING itinerary_id";
        try{
            int newItineraryId = jdbcTemplate.queryForObject(sql, int.class, newItinerary.getItineraryName(), newItinerary.getItineraryDate(), newItinerary.getItineraryStartingPoint());
            newItinerary.setItineraryId(newItineraryId);

            String sqlForTripTable = "INSERT INTO trips (user_id, itinerary_id) VALUES (?,?)";
            int userId = getUserIdByUsername(username);
            jdbcTemplate.update(sqlForTripTable, userId, newItineraryId);
        }
        catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException();
        }
//        catch (Exception e){
//            throw new RuntimeException();
//        }
    }

    @Override
    public void deleteItinerary(int id) {
        String sql = "DELETE FROM destinations WHERE itinerary_id = ?; DELETE FROM trips WHERE itinerary_id = ?;DELETE FROM itinerary WHERE itinerary_id = ?";
        try{
            jdbcTemplate.update(sql,id, id, id);
        }
        catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException();
        }
    }

    @Override
    public List<Itinerary> getAllItineraries(int userId) {
        List <Itinerary> resultList = new ArrayList<>();
        String sql = "SELECT * FROM itinerary " +

                " JOIN trips ON itinerary.itinerary_id = trips.itinerary_id" +

                " WHERE trips.user_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
              resultList.add(mapRowToItinerary(results));
            }
            return resultList;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public void updateItinerary(Itinerary newItinerary) {
        String sqlUpdateItinerary = "UPDATE itinerary" +
                " SET itinerary_name = ?," +
                " itinerary_date = ?," +
                " itinerary_starting_point = ?" +
                " WHERE itinerary_id = ?";
        jdbcTemplate.update(sqlUpdateItinerary, newItinerary.getItineraryName(), newItinerary.getItineraryDate(), newItinerary.getItineraryStartingPoint(), newItinerary.getItineraryId());
    }

    @Override
    public Itinerary getSpecificItinerary(int itineraryId) {
        String getItinerarySql = "SELECT * FROM itinerary WHERE itinerary_id = ?";
        Itinerary specificItinerary = new Itinerary();
        SqlRowSet results = jdbcTemplate.queryForRowSet(getItinerarySql, itineraryId);

        try {
            if (results.next()) {
                specificItinerary = mapRowToItinerary(results);
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
        return specificItinerary;
    }
    public List<Itinerary> getAllItinerariesByUserName(String userName) {
        int userId = getUserIdByUsername(userName);
        List <Itinerary> resultList = new ArrayList<>();
        String sql = "SELECT * FROM itinerary " +

                " JOIN trips ON itinerary.itinerary_id = trips.itinerary_id" +

                " WHERE trips.user_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                resultList.add(mapRowToItinerary(results));
            }
            return resultList;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public List<Itinerary> getSharedItineraries(int userid) {
        List<Itinerary> sharedItineraries = new ArrayList<>();
        String sharedSql = "SELECT * FROM itinerary" +
                " JOIN trips on trips.itinerary_id = itinerary.itinerary_id" +
                " JOIN shared_trips ON shared_trips.trip_id = trips.trip_id" +
                " WHERE user1_id = ? OR user2_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sharedSql, userid, userid);
        while(results.next()){
            sharedItineraries.add(mapRowToItinerary(results));
        }
        return sharedItineraries;
    }

    @Override
    public List<Itinerary> getCombinedItineraries(int userId) {
        List<Itinerary> comboList = new ArrayList<>();
        List<Itinerary> itineraries = getAllItineraries(userId);
        List<Itinerary> sharedItineraries = getSharedItineraries(userId);

        for (int i = 0; i < itineraries.size() ; i++) {
            comboList.add(itineraries.get(i));
        }
        for (int j = 0; j < sharedItineraries.size(); j++) {
            comboList.add(sharedItineraries.get(j));
        }

        return comboList;
    }

    public Itinerary mapRowToItinerary(SqlRowSet rowSet) {
        Itinerary itinerary = new Itinerary();
        itinerary.setItineraryId(rowSet.getInt("itinerary_id"));
        itinerary.setItineraryName(rowSet.getString("itinerary_name"));
        itinerary.setItineraryDate(rowSet.getDate("itinerary_date").toLocalDate());
        itinerary.setItineraryStartingPoint(rowSet.getString("itinerary_starting_point"));
        return itinerary;
    }
}
