import {loadDailyValues} from './index'
export const CHART_DAILY_UNSELECT_COMPANY = "chart-daily-company-unselected";
export const CHART_DAILY_SELECT_COMPANY = "chart-daily-company-selected";

export function unselectCompany(companyName){
    return {
        type: CHART_DAILY_UNSELECT_COMPANY,
        payload: companyName,
    }
}
export function selectCompany(companyName){
    var event = loadDailyValues([companyName]);

    return {
        type: CHART_DAILY_SELECT_COMPANY,
        payload: event.payload,
        meta: {companyName}
    }
}