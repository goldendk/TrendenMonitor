package org.goldenworkshop.warhammer.underhive.cdi;

import org.goldenworkshop.necromunda.underhive.deck.DeckController;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.cdi.GlobalProducer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
@RequestScoped
public class DeckControllerBean implements Serializable {

    @Inject
    @GlobalProducer
    TacticCardDAO tacticCardDAO;


    @PostConstruct
    public void postConstruct(){

    }

    public DeckController getDeckController() {
        return new DeckController(()->tacticCardDAO);
    }

}
