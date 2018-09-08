package org.goldenworkshop.warhammer.underhive.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;
import org.apache.commons.collections4.IteratorUtils;
import org.bson.Document;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.necromunda.underhive.deck.CardDeck;
import org.goldenworkshop.necromunda.underhive.deck.CardDraw;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.impl.AbstractMongoDAO;
import org.goldenworkshop.trenden.model.impl.MongoDAOHelper;


import javax.naming.NameParser;
import java.util.Collection;
import java.util.List;

public class MongoUnderHiveDAO extends AbstractMongoDAO implements TacticCardDAO {

    protected MongoCollection<TacticCard> cardCollection;
    private MongoCollection<Document> deckCollection;
    @Override
    public void initialize() throws Exception {
        super.initialize();
        loadCardCollection();
        loadDeckCollection();
    }

    @Override
    protected List<MongoCollection> handleReset() {
        cardCollection.drop();
        deckCollection.drop();
        loadCardCollection();
        loadDeckCollection();

        return null;
    }

    private void loadDeckCollection() {
        deckCollection = db.getCollection("underhive-tactic-decks");
    }

    private void loadCardCollection() {
        cardCollection = db.getCollection("people", TacticCard.class);
    }

    @Override
    public CardDeck loadActiveDeck(String userId) {
        return null;
    }

    @Override
    public Collection<TacticCard> loadValidCards() {
        MongoCursor<TacticCard> iterator = cardCollection.find().iterator();
        List<TacticCard> tacticCards = IteratorUtils.toList(iterator);
        return tacticCards;
    }

    @Override
    public void save(CardDeck cardDeck) {

    }

    @Override
    public void insertDraw(String id, CardDraw draw) {

    }

    @Override
    public List<CardDeck> loadLastNDecks(String userId, int count) {
        return null;
    }
}
