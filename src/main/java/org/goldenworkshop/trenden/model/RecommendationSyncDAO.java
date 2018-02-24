package org.goldenworkshop.trenden.model;

import java.util.List;
import java.util.Map;

public interface RecommendationSyncDAO extends ExternalInterface{

    /**
     * Saves the period object, updates it if it already exists.
     * @param period
     */
    void upsert(RecommendationPeriod period);

    /**
     * Load the recommendations that are not closed yet. A recommendation is recognized by its {@link Recommendation#name}.
     * @return - map of all open recommendations.
     */
    Map<String, RecommendationPeriod> loadOpenRecommendationPeriods();

    /**
     * Test interface - do not use in production
     * @param companyCName
     * @return
     */
    List<RecommendationPeriod> loadPeriodsByName(String companyCName);
}
