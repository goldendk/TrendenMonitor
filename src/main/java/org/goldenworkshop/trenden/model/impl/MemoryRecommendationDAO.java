package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;

import javax.enterprise.inject.Alternative;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@Alternative
public class MemoryRecommendationDAO implements RecommendationSyncDAO {

    Set<RecommendationPeriod> periods = new LinkedHashSet<>();
    Map<String, List<Recommendation>> recommendations = new HashMap<>();
    @Override
    public void upsert(RecommendationPeriod period) {
        periods.add(period);
    }

    @Override
    public Map<String, RecommendationPeriod> loadOpenRecommendationPeriods() {
        Map<String, RecommendationPeriod> collect = periods.stream()
                .filter(e->e.getEndSignal() == null )
                .collect(Collectors.toMap(RecommendationPeriod::getName, Function.identity()));
        return collect;
    }

    @Override
    public List<RecommendationPeriod> loadPeriodsByName(String companyCName) {
        return periods.stream().filter(e->e.getName().equalsIgnoreCase(companyCName)).collect(Collectors.toList());
    }

    @Override
    public void saveRecommendation(Recommendation recommendation) {
        recommendations.putIfAbsent(recommendation.getName(), new ArrayList<>());

        recommendations.get(recommendation.getName()).add(recommendation);
    }

    @Override
    public void initialize() throws Exception {
        //nothing.
    }

    @Override
    public void shutdown() throws Exception {
        //nothing.
    }
}
