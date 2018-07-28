import {buildGet} from "./AxiosHelper";
import {formToQueryString} from '../Util'
export const RECOMMENDATION_PERIODS_LOADED = "recommendation-periods-loaded";

export function loadRecommendationPeriods(event){


    var url = '/rest/recommendation-period';
    if(event != null){
        var query = formToQueryString(event.target);
        url += "?" + query;
    }

    let promise = buildGet(url, true);
    return {
        type: RECOMMENDATION_PERIODS_LOADED,
        payload: promise
    }
}