package org.goldenworkshop.trenden.model;

import org.goldenworkshop.trenden.controller.RecommendationController;
import org.goldenworkshop.trenden.model.impl.EuroInvestorRecommendationSource;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;
import org.goldenworkshop.trenden.servlet.ServletContextListener;

public class SyncRecommendationsJob extends StandardTrendenJob {
    @Override
    protected void executeJob() {
        RecommendationController recommendationController = new RecommendationController();
        recommendationController.setRecommendationSource(new EuroInvestorRecommendationSource());
        recommendationController.setSyncDao(ServletContextListener.dao);
        recommendationController.syncRecommendations();

    }
}
