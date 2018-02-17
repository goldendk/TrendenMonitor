package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationPeriodListener;

public class ThrowExceptionRecommendationListener implements RecommendationPeriodListener {
    @Override
    public void onPeriodCreate(RecommendationPeriod period) {
        throw new RuntimeException("create");
    }

    @Override
    public void onPeriodComplete(RecommendationPeriod period) {
        throw new RuntimeException("complete");
    }

    @Override
    public void finish() {
        throw new RuntimeException("finish");
    }
}
