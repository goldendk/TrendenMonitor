package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationPeriodListener;

public class EmailNotificationListener implements RecommendationPeriodListener {

    boolean sendEmail = false;
    @Override
    public void onPeriodCreate(RecommendationPeriod period) {
        sendEmail = true;
    }

    @Override
    public void onPeriodComplete(RecommendationPeriod period) {
        sendEmail = true;
    }

    @Override
    public void finish() {

    }
}
