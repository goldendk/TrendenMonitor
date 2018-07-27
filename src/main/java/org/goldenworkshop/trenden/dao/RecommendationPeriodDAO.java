package org.goldenworkshop.trenden.dao;

import org.goldenworkshop.trenden.model.RecommendationPeriod;

import java.util.Collection;
import java.util.Date;

public interface RecommendationPeriodDAO {
    Collection<RecommendationPeriod> loadPeriodWindow(Date sinceDate, Date toDate);
}
