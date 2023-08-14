package com.techelevator.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcDestinationDao implements DestinationDao {

    private JdbcTemplate template;

    public JdbcDestinationDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void addDestination(int itineraryId, int landmarkId) {
        String newDestinationSql = "INSERT INTO destinations(itinerary_id, landmark_id) VALUES(?,?)";
        try {
            template.update(newDestinationSql, itineraryId, landmarkId);
        } catch (DataAccessException e) {
            System.out.println("Already in Trip");
        }

    }

    @Override
    public void deleteDestination(int itineraryId, int landmarkId) {
        String removeDestination = "DELETE FROM destinations WHERE itinerary_id = ? AND landmark_id = ?";
        template.update(removeDestination, itineraryId, landmarkId);
    }
}
