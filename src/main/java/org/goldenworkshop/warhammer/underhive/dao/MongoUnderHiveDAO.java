package org.goldenworkshop.warhammer.underhive.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.apache.commons.collections4.IteratorUtils;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.necromunda.underhive.deck.CardDeck;
import org.goldenworkshop.necromunda.underhive.deck.CardDraw;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.model.impl.AbstractMongoDAO;

import java.util.Collection;
import java.util.List;

public class MongoUnderHiveDAO extends AbstractMongoDAO implements TacticCardDAO {

    protected MongoCollection<TacticCard> cardCollection;
    private MongoCollection<CardDeck> deckCollection;

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
        deckCollection = db.getCollection("underhive-tactic-decks", CardDeck.class);
    }

    private void loadCardCollection() {
        cardCollection = db.getCollection("people", TacticCard.class);
    }

    @Override
    public CardDeck loadActiveDeck(String userId) {
        CardDeck first = deckCollection.find(
                Filters.and(Filters.eq("user", userId),
                        Filters.eq("active", true))).first();
        return first;
    }

    @Override
    public Collection<TacticCard> loadValidCards() {
        MongoCursor<TacticCard> iterator = cardCollection.find().iterator();
        List<TacticCard> tacticCards = IteratorUtils.toList(iterator);
        return tacticCards;
    }

    @Override
    public void save(CardDeck cardDeck) {

        if (cardDeck.getId() == null) {
            deckCollection.insertOne(cardDeck);
        } else {
            upsertDocument(cardDeck.getId(), cardDeck, deckCollection);
        }

    }

    @Override
    public void insertDraw(String id, CardDraw draw) {
        CardDeck id1 = deckCollection.find(Filters.eq("_id", id)).first();
        id1.getDraws().add(draw);
        save(id1);
    }

    @Override
    public List<CardDeck> loadLastNDecks(String userId, int count) {
        MongoCursor<CardDeck> iterator = deckCollection.find(Filters.eq("user", userId)).limit(count).sort(Sorts.descending("_id")).iterator();
        List<CardDeck> cardDecks = IteratorUtils.toList(iterator);
        return cardDecks;
    }
}
