package org.goldenworkshop.warhammer.underhive.dao;

import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.trenden.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    }
}