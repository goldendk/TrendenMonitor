package org.goldenworkshop.trenden.model;

import java.util.List;
import java.util.Map;

public interface RecommendationSyncDAO {

    void save(RecommendationPeriod period);

    /**
     * Load the recommendations that are not closed yet. A recommendation is recognized by its {@link Recommendation#name}.
     * @return - map of all open recommendations.
     */
    Map<String, RecommendationPeriod> loadOpenRecommendationPeriods();

    List<RecommendationPeriod> loadPeriodsByName(String companyCName);
}
