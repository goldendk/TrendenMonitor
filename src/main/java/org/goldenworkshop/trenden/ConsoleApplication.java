package org.goldenworkshop.trenden;

import org.goldenworkshop.trenden.controller.RecommendationController;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.impl.EuroInvestorRecommendationSource;
import org.goldenworkshop.trenden.model.impl.MemoryRecommendationDAO;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;

import java.math.BigDecimal;
import java.util.Iterator;

public class ConsoleApplication {

    public static void main(String... args) throws Exception {
        System.setProperty("APPENV", "local");

        RecommendationController recommendationController = new RecommendationController();
        recommendationController.setRecommendationSource(new EuroInvestorRecommendationSource());

        MongoRecommendationDAO syncDao = new MongoRecommendationDAO();
        syncDao.initialize();
        recommendationController.setSyncDao(syncDao);

        try{
            recommendationController.syncRecommendations();
        }
        finally {
            syncDao.shutdown();
        }

    }

}
