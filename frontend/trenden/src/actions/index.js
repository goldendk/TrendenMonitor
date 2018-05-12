export const AUTH_CHANGE_EVENT = "auth-change";

export function onAuthChange(loggedIn){

    return {
        type: AUTH_CHANGE_EVENT,
        payload: loggedIn
    }

}