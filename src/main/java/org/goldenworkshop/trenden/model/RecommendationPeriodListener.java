package org.goldenworkshop.trenden.model;

public interface RecommendationPeriodListener {
    void onPeriodCreate(RecommendationPeriod period);
    void onPeriodComplete(RecommendationPeriod period);
    void finish();
}
