package org.goldenworkshop.warhammer.underhive.dao;

import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.necromunda.underhive.deck.CardDeck;
import org.goldenworkshop.trenden.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class MongoUnderHiveDAOTest extends BaseTest {

    MongoUnderHiveDAO mongoUnderHiveDAO;
    @Before
    public void before() throws Exception {
        mongoUnderHiveDAO = new MongoUnderHiveDAO();
        mongoUnderHiveDAO.initialize();

    }
    @After
    public void after() throws Exception {
        mongoUnderHiveDAO.resetTheDb("yes-i-am-testing");
        mongoUnderHiveDAO.shutdown();
    }

    @Test
    public void shouldLoadCards() {
        TacticCard card = new TacticCard();
        card.setName("bar");
        card.setDescription("bar description");
        card.setActivation("bar activation");
        card.setDescription("bar desc");
        card.setGangAffiliation("AB");
        card.setSourceDeck("BA");
        card.setZoneMortalisOnly(true);

        mongoUnderHiveDAO.cardCollection.insertOne(card);

        Collection<TacticCard> tacticCards = mongoUnderHiveDAO.loadValidCards();

        System.out.println(tacticCards);
        System.out.println(tacticCards.size());

    }

    @Test
    public void saveCarDeck(){

        TacticCard tacticCard = new TacticCard() {{
            setGangAffiliation("A");
            setName("A");
        }};
        mongoUnderHiveDAO.save(tacticCard);

        Collection<TacticCard> tacticCards = mongoUnderHiveDAO.loadValidCards();

        CardDeck cardDeck = new CardDeck(Arrays.asList(tacticCards.iterator().next()));
        cardDeck.setUser("foo");
        mongoUnderHiveDAO.save(cardDeck);

        CardDeck cardDeck1 = mongoUnderHiveDAO.loadActiveDeck("foo");
        CardDeck first = mongoUnderHiveDAO.deckCollection.find().first();
        System.out.println(first);

        assertNotNull("Should load from database by user", cardDeck1);
        assertNotNull("should have id", cardDeck1.getId());
        System.out.println(cardDeck1);
    }
}