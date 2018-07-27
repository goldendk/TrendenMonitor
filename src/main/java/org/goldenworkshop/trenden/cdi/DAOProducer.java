package org.goldenworkshop.trenden.cdi;

import org.goldenworkshop.trenden.dao.RecommendationPeriodDAO;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.servlet.ServletContextListener;
import org.goldenworkshop.trenden.view.rest.Application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class DAOProducer {
    @Produces
    @ApplicationScoped
    @GlobalProducer
    public RecommendationSyncDAO createDAO(){
        return ServletContextListener.dao;
    }

    @Produces
    @ApplicationScoped
    @GlobalProducer
    public RecommendationPeriodDAO createPeriodDAO(){
        return ServletContextListener.dao;
    }
}
