package org.goldenworkshop.trenden.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.model.*;

import java.util.*;

public class TrendenSyncController {
    private static Logger logger = LogManager.getLogger(TrendenSyncController.class);

    private RecommendationSource recommendationSource;
    private RecommendationSyncDAO syncDao;

    private List<RecommendationPeriodListener> listeners = new ArrayList<>();

    public void syncRecommendations() {

        Iterator<Recommendation> recommendationIterator = recommendationSource.iterator();
        Map<String, RecommendationPeriod> openRecommendations = syncDao.loadOpenRecommendationPeriods();
        logger.info("Open recommendation: " + openRecommendations.size());
        int recommendationCount = 0;
        while (recommendationIterator.hasNext()) {
            logger.info("Recommendation count " + ++recommendationCount);



            Recommendation recommendation = recommendationIterator.next();
            syncDao.saveRecommendation(recommendation);
            Company company = syncDao.getCompany(recommendation.getName());
            if(company == null){
                syncDao.saveCompany(new Company(recommendation.getName()));
            }


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
            closingRecommendation.setCreated(new Date());
            closingRecommendation.setLatestValue(toClose.getLatestValue());
            closingRecommendation.setSignal(Signal.MISSING);
            closingRecommendation.setSignalDate(new Date());
            logger.warn("Forcing closing recommendation period " + toClose.getName());
            this.endRecommendationPeriod(closingRecommendation, toClose);
        }
        logger.info("Calling listeners");
        for(RecommendationPeriodListener listener : listeners){
            try{
                listener.finish();
            }catch (Exception e){
                logger.error("Exception when calling finish on RecommendationListener", e);
            }
        }
    }

    private void updateRecomendationPeriod(Recommendation recommendation, RecommendationPeriod period) {
        period.setUpdated(new Date());
        period.setPeriodDays(recommendation.getDays());
        period.setLatestValue(recommendation.getLatestValue());
        period.setChangePercent(recommendation.getChange());
        logger.info("Updated recommendation period to " + period);
    }

    private void endRecommendationPeriod(Recommendation recommendation, RecommendationPeriod period) {
        period.setEndDate(recommendation.getSignalDate());
        period.setEndValue(recommendation.getLatestValue());
        period.setEndSignal(recommendation.getSignal());
        period.setUpdated(new Date());
        logger.info("Ended recommendation period: " + period);
        for(RecommendationPeriodListener listener : listeners){
            try{
                listener.onPeriodComplete(period);
            }catch (Exception e){
                logger.error( "Exception triggers listener for ending period: " + period, e);
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

        logger.info("Created recommendation period: " + period);
        for (RecommendationPeriodListener listener : listeners) {
            try {
                listener.onPeriodCreate(period);
            } catch (Exception e) {
                logger.error( "Exception triggering listener for creating period: " + period, e);
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
