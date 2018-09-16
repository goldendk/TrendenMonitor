import {buildGet, buildPost, buildDelete} from "./AxiosHelper";
import {formToQueryString} from '../Util'

export const DECK_LISTS_LOADED = "deck-lists-loaded";
export const TACTIC_CARD_LIST_LOADED = "tactic-card-list-loaded";
export const UNDERHIVE_META_LOADED = "underhive-meta-loaded";
export const TACTIC_CARD_SELECTED = "underhive-TACTIC_CARD_SELECTED";
export const CARD_DRAW_SELECTED = "underhive-CARD_DRAW_SELECTED";
export const CARD_DRAW_UNDO = "underhive-CARD_DRAW_UNDO";
export const USE_TACTIC_CARD = "underhive-USE_TACTIC_CARD";
export const USE_TACTIC_CARD_UNDO = "underhive-USE_TACTIC_CARD_UNDO";

export const CARD_DRAW_EVENT = "underhive-card-draw-event";
const REST_PATH = '/rest/warhammer/underhive';

export function loadCardDeckList(clickEvent) {

    var url = REST_PATH + "/deck/list";
    if (clickEvent != null) {
        var query = formToQueryString(clickEvent.target);
        url += "?" + query;
    }

    let promise = buildGet(url, true);
    return {
        type: DECK_LISTS_LOADED,
        payload: promise
    }


}

export function loadUnderHiveMeta() {
    var url = REST_PATH + "/meta";

    let promise = buildGet(url, true);

    return {
        type: UNDERHIVE_META_LOADED,
        payload: promise
    }

}

export function drawRandomCards(clickEvent) {
    var url = REST_PATH + "/deck/draw/random";
    if (clickEvent != null) {
        var query = formToQueryString(clickEvent.target);
        url += "?" + query;
    }

    let promise = buildPost(url, null, true);
    return {
        type: CARD_DRAW_EVENT,
        payload: promise
    }
}

export function undoDraw(deckId, idx) {
    var url = REST_PATH + "/deck/draw?deckId=" + deckId + "&drawIdx=" + idx;

    let promise = buildDelete(url, true);

    return (dispatch) => {
        console.log('In redux-thunk dispatch block.');

        promise.then((response) => {
            console.log('In redux-thunk response block: ' + response.status);
            if (response.status === 200) {
                console.log("undo went ok");
                dispatch({
                    type: CARD_DRAW_UNDO,
                    payload: response.data
                });
            }
        });
    };

}

export function archiveDeck(deckId) {
    var url = REST_PATH + "/deck/archive?deckId=" + deckId;

    let promise = buildPost(url, null, true);
    return {
        type: DECK_LISTS_LOADED,
        payload: promise,
        meta: {
            source: 'archive-deck'
        }
    }
}

export function loadTacticCardList(filterUsed) {
    var url = REST_PATH + "/deck/cards?filterUsed="+filterUsed;
    let promise = buildGet(url, true);

    return {
        type: TACTIC_CARD_LIST_LOADED,
        payload: promise
    }
}

export function toggleTacticCard(cardId) {
    return {
        type: TACTIC_CARD_SELECTED,
        payload: cardId
    }
}

export function drawSelectedTacticCards(selectedCards, successCallback, errorCallback) {

    let strings = Object.keys(selectedCards);

    let params = strings.join("&cardId=");
    var url = REST_PATH + "/deck/draw/selected?cardId=" + params;
    let promise = buildPost(url);

    return (dispatch) => {
        promise.then((response) => {
            if (response.status === 200) {
                successCallback();
                dispatch({
                    type: CARD_DRAW_SELECTED,
                    payload: response.data
                });
            }
            else {
                errorCallback();
            }
        });
    };

}

export function toggleTacticCardUsage(deckId, drawIdx, cardId, newState, successCb, errorCb){

    var url = REST_PATH + "/deck/draw/use-card?deckId=" + deckId + "&drawIdx="+drawIdx + "&cardId="+ cardId;


    let promise = newState ? buildPost(url) : buildDelete(url);

    return (dispatch) =>{
        promise.then((response) =>{
            if(response.status === 200){
                if(successCb != null){
                    successCb();
                }
                dispatch({
                    type: newState ? USE_TACTIC_CARD : USE_TACTIC_CARD_UNDO,
                    payload: response.data
                });
            }
            else{
                console.log("could not toggle tactic card!")
                if(errorCb){
                    errorCb();
                }
            }
        });
    }

}