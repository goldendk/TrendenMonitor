package org.goldenworkshop.warhammer.underhive.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.necromunda.underhive.deck.CardDeck;
import org.goldenworkshop.necromunda.underhive.deck.CardDraw;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.model.impl.AbstractMongoDAO;

import java.util.Collection;
import java.util.List;

public class MongoUnderHiveDAO extends AbstractMongoDAO implements TacticCardDAO {
    private static Logger logger = LogManager.getLogger();
    protected MongoCollection<TacticCard> cardCollection;
    protected MongoCollection<CardDeck> deckCollection;

    @Override
    public void initialize() throws Exception {
        super.initialize();
        loadCardCollection();
        loadDeckCollection();
    }

    @Override
    protected List<MongoCollection> handleReset() {
        logger.warn("Resetting collections for MongoUnderHiveDAO.");
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
                Filters.and(Filters.eq("archived", false),
                        Filters.eq("user", userId)
                        )

                        ).sort(Sorts.descending("_id")).first();
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
            cardDeck.setId(ObjectId.get().toHexString());
            deckCollection.insertOne(cardDeck);
        } else {
            upsertDocument(cardDeck.getId(), cardDeck, deckCollection);
        }

    }



    @Override
    public List<CardDeck> loadLastNDecks(String userId, int count) {
        MongoCursor<CardDeck> iterator = deckCollection.find(Filters.and(Filters.eq("user", userId)
        , Filters.eq("archived", false))).limit(count).sort(Sorts.descending("_id")).iterator();
        List<CardDeck> cardDecks = IteratorUtils.toList(iterator);
        return cardDecks;
    }

    @Override
    public void save(TacticCard tacticCard) {
        if(tacticCard.getId() == null){
            tacticCard.setId(ObjectId.get().toHexString());
            cardCollection.insertOne(tacticCard);
        }
        else{
            upsertDocument(tacticCard.getId(), tacticCard, cardCollection);
        }

    }

    @Override
    public CardDeck loadDeck(String deckId) {
        return deckCollection.find(Filters.eq("_id", new ObjectId(deckId))).first();
    }
}
