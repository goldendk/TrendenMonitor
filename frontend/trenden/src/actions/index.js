//libraries
import axios from 'axios';

export const USER_STATE_LOADED = "user-state-loaded";
export const USER_STATE_LOGOUT = "user-state-logout";
export const CHART_COMPANIES_LOADED = "chart-companies-loaded";
export const CHART_DAILY_LOADED = "chart-daily-loaded";

function buildGet(url, withCredentials){
    const request = axios.get(url, {
        headers: {
            'Accept': "application/json"
        }
        , withCredentials:  withCredentials | false
    });
    return request;
}


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
    const request = buildGet("/rest/trenden/companies");
    return {
        type: CHART_COMPANIES_LOADED,
        payload: request
    }
}
export function loadDailyValues(selectedCompanies){
var url = "/rest/trenden/daily-values?foo=bar" + selectedCompanies.map((e)=>{return "&companyNames=" + encodeURIComponent(e)}).join("");
console.log("Calling url: " + url);
    const request = buildGet(url);

    return {
        type: CHART_DAILY_LOADED,
        payload: request
    }
}