package org.goldenworkshop.trenden;

import org.goldenworkshop.trenden.controller.RecommendationController;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.impl.EuroInvestorRecommendationSource;
import org.goldenworkshop.trenden.model.impl.MemoryRecommendationDAO;

import java.math.BigDecimal;
import java.util.Iterator;

public class Application {

    public static void main(String... args){

//        RecommendationController recommendationController = new RecommendationController();
//        recommendationController.setRecommendationSource(new EuroInvestorRecommendationSource());
//        recommendationController.setSyncDao(new MemoryRecommendationDAO());
//        recommendationController.syncRecommendations();

    new BigDecimal("10,300.00");
        EuroInvestorRecommendationSource source = new EuroInvestorRecommendationSource();
        Iterator<Recommendation> iterator = source.iterator();
        while(iterator.hasNext()){
            Recommendation next = iterator.next();
            System.out.println(next);
        }

    }

}
