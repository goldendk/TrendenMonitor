
export const CHART_DAILY_UNSELECT_COMPANY = "chart-daily-company-unselected";
export const CHART_DAILY_SELECT_COMPANY = "chart-daily-company-selected";

export function unselectCompany(nameOfCompany){
    return {
        type: CHART_DAILY_UNSELECT_COMPANY,
        payload: nameOfCompany
    }
}
export function selectCompany(nameOfCompany){
    return {
        type: CHART_DAILY_SELECT_COMPANY,
        payload: nameOfCompany
    }
}