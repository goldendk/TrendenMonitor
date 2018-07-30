package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.BaseTest;
import org.goldenworkshop.trenden.TestHelper;
import org.goldenworkshop.trenden.dao.PeriodFilter;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.model.Signal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MongoRecommendationDAOTest extends BaseTest {
    private MongoRecommendationDAO syncDAO;
    Calendar today = Calendar.getInstance();
    Calendar twoDaysAgo = null;

    @Before
    public void setUp() throws Exception {
        syncDAO = new MongoRecommendationDAO();
        syncDAO.initialize();
        syncDAO.dropTheDb("yes-i-am-testing");

        twoDaysAgo =Calendar.getInstance();
        twoDaysAgo.add(Calendar.DATE, -2);
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
    public void loadPeriodsByFilter(){
        PeriodFilter filter = new PeriodFilter();
        Calendar instance = Calendar.getInstance();
        filter.setToDate(instance);

        Calendar instance1 = Calendar.getInstance();
        instance1.add(Calendar.DATE, -90);
        filter.setFromDate(instance1.getTime());
        filter.setMaxStockPrice(2000);
        filter.setExcludeShorting(true);

        syncDAO.loadPeriodWindow(filter);
    }

    @Test
    public void saveNewPeriod() {

        RecommendationPeriod period = TestHelper.createRecommendationPeriod(twoDaysAgo, today);
        syncDAO.upsert(period);

        List<RecommendationPeriod> recommendationPeriods = syncDAO.loadPeriodsByName(TestHelper.COMPANY_A_NAME);
        RecommendationPeriod recommendationPeriod = recommendationPeriods.get(0);
        assertEquals(today.getTime(), recommendationPeriod.getEndDate());
        assertEquals(twoDaysAgo.getTime(), recommendationPeriod.getStartDate());

        assertEquals("Should have one period", 1, recommendationPeriods.size());

    }

    @Test
    public void updatePeriod() {
        saveNewPeriod();
        List<RecommendationPeriod> recommendationPeriods = syncDAO.loadPeriodsByName(TestHelper.COMPANY_A_NAME);
        RecommendationPeriod recommendationPeriod = recommendationPeriods.get(0);
        recommendationPeriod.setChangePercent("foo");
        syncDAO.upsert(recommendationPeriod);

        List<RecommendationPeriod> recommendationPeriods1 = syncDAO.loadPeriodsByName(TestHelper.COMPANY_A_NAME);
        assertEquals("Should only have 1 since we are upserting", 1, recommendationPeriods1.size());
        recommendationPeriod = recommendationPeriods1.get(0);
        assertEquals("foo", recommendationPeriod.getChangePercent());
    }

    @Test
    public void loadOpenPeriods() {

        RecommendationPeriod period = TestHelper.createRecommendationPeriod(twoDaysAgo, today);
        RecommendationPeriod period2 = TestHelper.createRecommendationPeriod(twoDaysAgo, today);
        period.setEndSignal(null);
        period2.setEndSignal(Signal.SELL);
        period2.setName(TestHelper.COMPANY_B_NAME);


        syncDAO.upsert(period);
        syncDAO.upsert(period2);

        Map<String, RecommendationPeriod> stringRecommendationPeriodMap = syncDAO.loadOpenRecommendationPeriods();
        assertEquals(1, stringRecommendationPeriodMap.size());
        assertNull("Only open periods should return", stringRecommendationPeriodMap.get(TestHelper.COMPANY_A_NAME).getEndSignal());

    }

}