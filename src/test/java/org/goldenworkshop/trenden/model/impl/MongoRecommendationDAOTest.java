package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.BaseTest;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MongoRecommendationDAOTest extends BaseTest {
    private RecommendationSyncDAO syncDAO;

    @Before
    public void setUp() throws Exception {
        syncDAO = new MongoRecommendationDAO();
        syncDAO.initialize();
    }

    @After
    public void tearDown() throws Exception {
        syncDAO.shutdown();
    }

    @Test
    public void shouldConnect() throws Exception {
        syncDAO.loadPeriodsByName("foo");
    }

    @Test
    public void saveNewPeriod() {

    }

    @Test
    public void updatePeriod() {

    }

    @Test
    public void loadOpenPeriods() {

    }

    @Test
    public void loadPeriodsByName() {

    }
}