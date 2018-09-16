import {
    DECK_LISTS_LOADED,
    UNDERHIVE_META_LOADED,
    CARD_DRAW_EVENT,
    TACTIC_CARD_LIST_LOADED,
    TACTIC_CARD_SELECTED,
    CARD_DRAW_SELECTED, CARD_DRAW_UNDO, USE_TACTIC_CARD_UNDO, USE_TACTIC_CARD
} from "../actions/underhiveActions";

export default function (state = {selectedTacticCards: {}}, action) {
    switch (action.type) {
        case UNDERHIVE_META_LOADED:
            const authenticated = (action.payload.status === 200);
            var newMeta = {...action.payload.data, gangMap: {}};

            newMeta.gangs.map(gang => {
                newMeta.gangMap[gang.gangSymbol] = gang;
            });

            return {
                ...state,
                meta: newMeta
            };
        case DECK_LISTS_LOADED:
            const ok = (action.payload.status === 200);
            console.log("reducer_underhive.js, HTTP status: " + ok);
            return {
                ...state, decks: action.payload.data
            };
        case CARD_DRAW_EVENT:
            const okDraw = (action.payload.status === 200);
            console.log("reducer_underhive.js, HTTP status: " + ok);
            var currentDecks =replaceDeck(state.decks, action.payload.data);
            return {
                ...state, decks: currentDecks
            };

        case TACTIC_CARD_LIST_LOADED:
            const okLoaded = (action.payload.status === 200);
            console.log("Loaded tactic card list " + okLoaded);

            var loadedCards = action.payload.data;


            return {
                ...state, tacticCardList: loadedCards
            };
        case TACTIC_CARD_SELECTED:

            var selectedCards = {...state.selectedTacticCards};
            console.log("Got tactic card selected event: " + JSON.stringify(action));
            if (state.selectedTacticCards[action.payload] == null) {
                selectedCards[action.payload] = true;
            }
            else {
                delete selectedCards[action.payload];
            }
            return {
                ...state, selectedTacticCards: selectedCards
            };

        case CARD_DRAW_SELECTED:
            var selectedCards = {};
            return {
                ...state, selectedTacticCards: selectedCards
            };
        case CARD_DRAW_UNDO:
            var deckId = action.payload.id;
            var currentDecks = [...state.decks];
            var targetIdx = null;
            var removed = currentDecks.map((deck, idx)=>{
               if( deck.id.localeCompare(deckId)===0){
                   targetIdx = idx;
                   return null;
               }
               return deck;
            });

            removed[targetIdx] = action.payload;

            return {
                ...state,decks: removed
            };
        case USE_TACTIC_CARD:
        case USE_TACTIC_CARD_UNDO:
            var currentDecks = replaceDeck(state.decks, action.payload);
            return{
                ...state, decks: currentDecks
            };
        default:
            return state;
    }

    function replaceDeck(decks, newDeck){
        var currentDecks = [...decks];
        if (currentDecks == null) {
            currentDecks = [];
        }
        if (currentDecks.length == 0) {
            currentDecks.push(newDeck);
        }
        else {
            currentDecks[0] = newDeck;
        }
        return currentDecks;
    }
}