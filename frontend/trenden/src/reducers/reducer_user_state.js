import {USER_STATE_LOADED, USER_STATE_LOGOUT} from "../actions";

export default function (state = {}, action) {
    switch (action.type) {
        case USER_STATE_LOADED:
            const authenticated = (action.payload.status == 200);
            return {
                ...state,
                authenticated: authenticated,
                user: (authenticated) ? action.payload.data : null
            };
        case USER_STATE_LOGOUT:
            return {
                authenticated: false,
                user: null
            };
        default:
            return state;
    }
}