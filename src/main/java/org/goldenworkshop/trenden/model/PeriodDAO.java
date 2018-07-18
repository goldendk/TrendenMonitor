package org.goldenworkshop.trenden.model;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 *
 * Container for interacting with RecommendationPeriod objects.
 *
 */
public interface PeriodDAO extends ExternalInterface{
    Collection<RecommendationPeriod> loadRecommendationPeriods(ZonedDateTime from, ZonedDateTime to);
}
