package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemoryRecommendationDAO implements RecommendationSyncDAO {

    Set<RecommendationPeriod> periods = new LinkedHashSet<>();
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
    public void initialize() throws Exception {
        //nothing.
    }

    @Override
    public void shutdown() throws Exception {
        //nothing.
    }
}
