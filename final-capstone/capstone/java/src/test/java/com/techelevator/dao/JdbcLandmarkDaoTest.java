package com.techelevator.dao;

import com.techelevator.model.Landmark;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcLandmarkDaoTest extends BaseDaoTests {

    private JdbcLandmarkDao jdbcLandmarkDao;

    @Test
    void getAllLandmarks() {
        int expected = 4;

        List<Landmark> actual = jdbcLandmarkDao.getAllLandmarks();

        Assert.assertEquals(actual.size(), expected);
    }
}