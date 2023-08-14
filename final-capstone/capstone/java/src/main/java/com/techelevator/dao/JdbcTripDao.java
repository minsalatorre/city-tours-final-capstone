package com.techelevator.dao;

import com.techelevator.model.Itinerary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcTripDao implements TripDao{

    private JdbcTemplate template;

    public JdbcTripDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void addTrip(int userId, int itineraryId) {
        String newTripSql = "INSERT INTO trips(user_id, itinerary_id) VALUES(?,?)";
        template.update(newTripSql, userId, itineraryId);
    }
}
