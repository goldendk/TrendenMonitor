package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.TestHelper;
import org.goldenworkshop.trenden.model.*;
import org.goldenworkshop.trenden.model.impl.MemoryRecommendationDAO;
import org.goldenworkshop.trenden.model.impl.ThrowExceptionRecommendationListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrendenSyncControllerTest {

    @Spy
    private TrendenSyncController controllerSpy;
    @Spy
    private MemoryRecommendationDAO recommendationDAOSpy;
    @Mock
    RecommendationSource recommendationSourceMock;
    List<Recommendation> list = new ArrayList<>();
    @Spy
    private ThrowExceptionRecommendationListener exceptionRecommendationListener;

    @Before
    public void before() {

        controllerSpy.setSyncDao(recommendationDAOSpy);
        controllerSpy.addListener(exceptionRecommendationListener);
        controllerSpy.setRecommendationSource(recommendationSourceMock);
        when(recommendationSourceMock.iterator()).thenAnswer((Answer<Iterator<Recommendation>>) invocation -> list.iterator());
        list.clear();
    }

    @Test
    public void shouldSaveOnFirstRun() {
        //setup
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_A_NAME, Signal.BUY));
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_B_NAME, Signal.SELL));
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_C_NAME, Signal.BUY));
        //business
        controllerSpy.syncRecommendations();

        Map<String, RecommendationPeriod> stringRecommendationPeriodMap = recommendationDAOSpy.loadOpenRecommendationPeriods();
        assertEquals(3, stringRecommendationPeriodMap.size());
        //pick one and assert.
        RecommendationPeriod recommendation = stringRecommendationPeriodMap.get(TestHelper.COMPANY_B_NAME);
        assertEquals(Signal.SELL, recommendation.getStartSignal());
        assertNull(recommendation.getEndSignal());
        assertEquals(100.0, recommendation.getStartValue().doubleValue(), 0.0001);
        assertEquals(200.0, recommendation.getLatestValue().doubleValue(), 0.0001);
        assertEquals(TestHelper.COMPANY_B_NAME, recommendation.getName());
        assertEquals(0, recommendation.getPeriodDays());
        assertNull(recommendation.getEndSignal());
        assertNull(recommendation.getEndDate());
        assertNull(recommendation.getEndValue());
        assertEquals("2.9%", recommendation.getChangePercent());
    }

    /**
     * Company c changes signal and should have ended current period and a new one created.
     */
    @Test
    public void shouldRecordChangeInSignal() {
        //setup
        shouldSaveOnFirstRun();
        list.clear();
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_A_NAME, Signal.BUY));
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_B_NAME, Signal.SELL));
        Recommendation sellRecommendation = TestHelper.createRecommendation(TestHelper.COMPANY_C_NAME, Signal.SELL);
        sellRecommendation.setDays(2);
        sellRecommendation.setSignalValue(new BigDecimal(300.0));
        list.add(sellRecommendation);
        //business
        controllerSpy.syncRecommendations();
        //verify
        Map<String, RecommendationPeriod> stringRecommendationPeriodMap = recommendationDAOSpy.loadOpenRecommendationPeriods();
        assertEquals(3, stringRecommendationPeriodMap.size());
        RecommendationPeriod period = stringRecommendationPeriodMap.get(TestHelper.COMPANY_C_NAME);
        assertEquals(Signal.SELL, period.getStartSignal());
        //find closed period.
        List<RecommendationPeriod> recommendationPeriods = recommendationDAOSpy.loadPeriodsByName(TestHelper.COMPANY_C_NAME);
        assertEquals(2, recommendationPeriods.size());
        assertEquals(Signal.BUY, recommendationPeriods.get(0).getStartSignal());
        assertEquals(Signal.SELL, recommendationPeriods.get(0).getEndSignal());
        assertEquals(Signal.SELL, recommendationPeriods.get(1).getStartSignal());
    }

    /**
     * Company B is removed from recommendation list and is therefor closed with result unknown signal type.
     */
    @Test
    public void shouldEndMissingRecommendations() {

        //setup
        shouldSaveOnFirstRun();
        list.clear();
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_A_NAME, Signal.BUY));
        list.add(TestHelper.createRecommendation(TestHelper.COMPANY_C_NAME, Signal.BUY));

        //business
        controllerSpy.syncRecommendations();

        //assert
        List<RecommendationPeriod> recommendationPeriods = recommendationDAOSpy.loadPeriodsByName(TestHelper.COMPANY_B_NAME);
        assertEquals(1, recommendationPeriods.size());
        RecommendationPeriod period = recommendationPeriods.get(0);
        assertEquals(Signal.MISSING, period.getEndSignal());
        assertNotNull(period.getEndValue());
        assertNotNull(period.getEndSignal());
        assertNotNull(period.getEndDate());


    }


}