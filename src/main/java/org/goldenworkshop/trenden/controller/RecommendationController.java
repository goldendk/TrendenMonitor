package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.model.*;
import org.goldenworkshop.trenden.model.impl.MemoryRecommendationDAO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationController {
    private static Logger logger = Logger.getLogger(RecommendationController.class.getName());

    private RecommendationSource recommendationSource;
    private RecommendationSyncDAO syncDao;

    private List<RecommendationPeriodListener> listeners = new ArrayList<>();

    public void syncRecommendations() {

        Iterator<Recommendation> recommendationIterator = recommendationSource.iterator();
        Map<String, RecommendationPeriod> openRecommendations = syncDao.loadOpenRecommendationPeriods();

        while (recommendationIterator.hasNext()) {
            Recommendation recommendation = recommendationIterator.next();
            String name = recommendation.getName();
            RecommendationPeriod period = openRecommendations.get(name);

            if (period == null) {//new recommendation
                period = this.createRecommendationPeriod(recommendation);
                updateRecomendationPeriod(recommendation, period);
            } else {
                if (period != null && recommendation.getSignal() != period.getStartSignal()) {
                    this.updateRecomendationPeriod(recommendation, period);
                    this.endRecommendationPeriod(recommendation, period);
                    syncDao.upsert(period);
                    //start new period
                    period = createRecommendationPeriod(recommendation);
                }
                else if(period != null && recommendation.getSignal() == period.getStartSignal()){
                    updateRecomendationPeriod(recommendation, period);
                }
            }
            syncDao.upsert(period);
            openRecommendations.remove(name);
        }
        for(RecommendationPeriod toClose : openRecommendations.values()){
            Recommendation closingRecommendation = new Recommendation();
            closingRecommendation.setLatestValue(toClose.getLatestValue());
            closingRecommendation.setSignal(Signal.MISSING);
            closingRecommendation.setSignalDate(new Date());
            this.endRecommendationPeriod(closingRecommendation, toClose);
        }

        for(RecommendationPeriodListener listener : listeners){
            try{
                listener.finish();
            }catch (Exception e){
                logger.log(Level.SEVERE, "Exception when calling finish on RecommendationListener", e);
            }
        }
    }

    private void updateRecomendationPeriod(Recommendation recommendation, RecommendationPeriod period) {
        period.setUpdated(new Date());
        period.setPeriodDays(recommendation.getDays());
        period.setLatestValue(recommendation.getLatestValue());
        period.setChangePercent(recommendation.getChange());
    }

    private void endRecommendationPeriod(Recommendation recommendation, RecommendationPeriod period) {
        period.setEndDate(recommendation.getSignalDate());
        period.setEndValue(recommendation.getLatestValue());
        period.setEndSignal(recommendation.getSignal());
        period.setUpdated(new Date());

        for(RecommendationPeriodListener listener : listeners){
            try{
                listener.onPeriodComplete(period);
            }catch (Exception e){
                logger.log(Level.SEVERE, "Exception triggers listener for ending period: " + period, e);
            }
        }
    }

    private RecommendationPeriod createRecommendationPeriod(Recommendation recommendation) {
        RecommendationPeriod period = new RecommendationPeriod();
        period.setName(recommendation.getName());
        period.setStartSignal(recommendation.getSignal());
        period.setStartDate(recommendation.getSignalDate());
        period.setStartValue(recommendation.getSignalValue());
        period.setCreated(new Date());

        for (RecommendationPeriodListener listener : listeners) {
            try {
                listener.onPeriodCreate(period);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception triggering listener for creating period: " + period, e);
            }
        }
        return period;
    }

    public void setRecommendationSource(RecommendationSource recommendationSource) {
        this.recommendationSource = recommendationSource;
    }

    public void setSyncDao(RecommendationSyncDAO syncDao) {
        this.syncDao = syncDao;
    }

    public void addListener(RecommendationPeriodListener listener) {
        this.listeners.add(listener);
    }
}
