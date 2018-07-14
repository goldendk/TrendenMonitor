package org.goldenworkshop.trenden;

import org.goldenworkshop.trenden.controller.TrendenSyncController;
import org.goldenworkshop.trenden.model.impl.EuroInvestorRecommendationSource;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;

public class ConsoleApplication {

    public static void main(String... args) throws Exception {
        System.setProperty("APPENV", "local");

        TrendenSyncController trendenSyncController = new TrendenSyncController();
        trendenSyncController.setRecommendationSource(new EuroInvestorRecommendationSource());

        MongoRecommendationDAO syncDao = new MongoRecommendationDAO();
        syncDao.initialize();
        trendenSyncController.setSyncDao(syncDao);

        try{
            trendenSyncController.syncRecommendations();
        }
        finally {
            syncDao.shutdown();
        }

    }

}
