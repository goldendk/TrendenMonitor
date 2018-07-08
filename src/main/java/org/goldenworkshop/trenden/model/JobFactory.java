package org.goldenworkshop.trenden.model;

import org.apache.commons.lang3.StringUtils;

public class JobFactory {

    public static final String SYNC_RECOMMENDATIONS = "sync-recommendations";
    public static final String[] JOB_KEY_LIST = {SYNC_RECOMMENDATIONS};

    public static TrendenJob build(String key){

        if(StringUtils.equals(SYNC_RECOMMENDATIONS, key)){
            return new SyncRecommendationsJob();
        }
        else{
            throw new IllegalArgumentException("Unknown key: " + key);
        }

    }
}
