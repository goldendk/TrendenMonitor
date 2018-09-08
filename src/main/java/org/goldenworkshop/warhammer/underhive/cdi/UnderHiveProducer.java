package org.goldenworkshop.warhammer.underhive.cdi;

import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.servlet.ServletContextListener;
import org.goldenworkshop.trenden.view.rest.Application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class UnderHiveProducer {

    @Produces
    @ApplicationScoped
    @GlobalProducer
    public TacticCardDAO createDAO(){
        return ServletContextListener.underHiveDAO;
    }



}

