package org.goldenworkshop.trenden.model;

import org.goldenworkshop.trenden.controller.TrendenSyncController;
import org.goldenworkshop.trenden.model.impl.EuroInvestorRecommendationSource;
import org.goldenworkshop.trenden.servlet.ServletContextListener;

public class SyncRecommendationsJob extends StandardTrendenJob {
    @Override
    protected void executeJob() {
        TrendenSyncController trendenSyncController = new TrendenSyncController();
        trendenSyncController.setRecommendationSource(new EuroInvestorRecommendationSource());
        trendenSyncController.setSyncDao(ServletContextListener.dao);
        trendenSyncController.syncRecommendations();

    }
}
