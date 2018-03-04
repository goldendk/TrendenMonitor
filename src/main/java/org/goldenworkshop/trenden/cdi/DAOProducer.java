package org.goldenworkshop.trenden.cdi;

import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.view.rest.Application;

import javax.enterprise.inject.Produces;

public class DAOProducer {

    @Produces
    public RecommendationSyncDAO createDAO(){
        return Application.
    }
}
