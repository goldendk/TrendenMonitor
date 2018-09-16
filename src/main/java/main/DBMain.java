package main;

import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.warhammer.underhive.dao.MongoUnderHiveDAO;

import java.util.Collection;

public class DBMain {
    public static void main(String... args) throws Exception {
        System.setProperty("APPENV", "local");

        MongoUnderHiveDAO dao = new MongoUnderHiveDAO();
        dao.initialize();

        Collection<TacticCard> tacticCards = dao.loadValidCards();
        System.out.println(tacticCards.size());
//
//        DeckController controller = new DeckController(()->dao);
//        String userId = "12345678900";
//     //   controller.draw(userId, 2, true, null);
//
//        CardDeck cardDeck = dao.loadActiveDeck(userId);
//        System.out.println(cardDeck.getDeck().size());
    }
}
