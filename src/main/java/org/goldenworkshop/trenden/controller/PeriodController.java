package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.model.*;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.*;

@RequestScoped
public class PeriodController {

    @Inject
    PeriodDAO periodDAO;

    /**
     * Loads a map of company names and lists of recommendation-periods.
     * @param from
     * @param to
     * @return
     */
    public Map<String, List<RecommendationPeriod>> loadPeriodPages(ZonedDateTime from, ZonedDateTime to){

        Collection<RecommendationPeriod> periods = periodDAO.loadRecommendationPeriods(from, to);
        Map<String, List<RecommendationPeriod>> result = new HashMap();
        for(RecommendationPeriod rp : periods){

            result.putIfAbsent(rp.getName(), new ArrayList<>());
            result.get(rp.getName()).add(rp);

        }

        for(String companyName : result.keySet()){
            result.get(companyName).sort(Comparator.comparing(RecommendationPeriod::getCreated));
        }
        return result;
    }


}
