import {AUTH_CHANGE_EVENT} from "../actions";

export default function(state = {}, action){
    switch(action.type){
        case AUTH_CHANGE_EVENT:
            return {
                authState: action.payload
            };
        default:
            return state;
    }
}