//libraries
import axios from 'axios';

export const USER_STATE_LOADED = "user-state-loaded";
export const USER_STATE_LOGOUT = "user-state-logout";


export function googleLoginAction(tokenId){
    const request = axios.get("/rest/application/auth/google?idToken=" + tokenId, {
        headers: {
            'Accept': "application/json"
        }
        , withCredentials: true
    });

    return {
        type: USER_STATE_LOADED,
        payload:  request
    }
}
export function googleLogoutAction(){
    return {
        type: USER_STATE_LOGOUT
    }
}
export function fetchCurrentSession(){
    const request = axios.get("/rest/application/session", {
        headers: {
            'Accept': "application/json"
        }, withCredentials: true
    });

    return {
        type: USER_STATE_LOADED,
        payload: request
    }
}

export function loadCompanyNames(){
    const request = axios.get("/rest/")
}