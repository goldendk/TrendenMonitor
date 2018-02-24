package org.goldenworkshop.trenden;

import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.Signal;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class TestHelper {
    public static final String COMPANY_A_NAME = "company_a";
    public static final String COMPANY_B_NAME = "company_b";
    public static final String COMPANY_C_NAME = "company_c";

    public static Recommendation createRecommendation(String companyAName, Signal signal) {
        Recommendation recommendation = new Recommendation();
        recommendation.setSignalValue(new BigDecimal(100.0));
        recommendation.setSignal(signal);
        recommendation.setSignalDate(new Date());
        recommendation.setDays(0);
        recommendation.setLatestValue(new BigDecimal(200.0));
        recommendation.setName(companyAName);
        recommendation.setChange("2.9%");

        return recommendation;
    }

    public static RecommendationPeriod createRecommendationPeriod(Calendar twoDaysAgo, Calendar today) {
        RecommendationPeriod period = new RecommendationPeriod();
        period.setStartSignal(Signal.BUY);
        period.setEndSignal(Signal.SELL);

        period.setCreated(twoDaysAgo.getTime());
        period.setUpdated(today.getTime());
        period.setStartDate(twoDaysAgo.getTime());
        period.setEndDate(today.getTime());
        period.setLatestValue(new BigDecimal("200.00"));
        period.setChangePercent("2.9%");
        period.setStartValue(new BigDecimal("100.00"));
        period.setEndValue(new BigDecimal("300.00"));
        period.setName(TestHelper.COMPANY_A_NAME);
        period.setPeriodDays(20);

        return period;
    }
}
