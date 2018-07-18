package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.model.PeriodDAO;
import org.goldenworkshop.trenden.model.RecommendationPeriod;

import java.time.ZonedDateTime;
import java.util.Collection;

public class MongoPeriodDAO extends AbstractMongoDAO implements PeriodDAO {
    @Override
    public void initialize() throws Exception {
        super.initialize();

    }

    @Override
    public Collection<RecommendationPeriod> loadRecommendationPeriods(ZonedDateTime from, ZonedDateTime to) {


        return null;
    }
}
