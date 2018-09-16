package org.goldenworkshop.warhammer.underhive.cdi;

import org.goldenworkshop.necromunda.underhive.deck.DeckController;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.servlet.ServletContextListener;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UnderHiveProducer {

    @Produces
    @ApplicationScoped
    @GlobalProducer
    public TacticCardDAO createDAO(){
        return ServletContextListener.underHiveDAO;
    }


}

