import {buildGet} from "./AxiosHelper";
export const RECOMMENDATION_PERIODS_LOADED = "recommendation-periods-loaded";

export function loadRecommendationPeriods(){
    var url = '/rest/recommendation-period';
    let promise = buildGet(url, true);
    return {
        type: RECOMMENDATION_PERIODS_LOADED,
        payload: promise
    }
}