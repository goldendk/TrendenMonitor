import {CHART_COMPANIES_LOADED, CHART_DAILY_LOADED} from "../actions";

export default function (state = {dailyValues: []}, action) {
    switch (action.type) {
        case CHART_COMPANIES_LOADED:
            var companies = null;
            var success = (action.payload.status === 200);
            if(success){
                companies = [...action.payload.data]
            }
            else{
                companies = state.companies ?  [...state.companies] : [];
            }
            return {
                ...state,
                companies: companies
            };

        case CHART_DAILY_LOADED:
            var dailyValues = null;
            if(action.payload.status === 200){
                dailyValues = [...action.payload.data.dataList];
            }
            else{
                dailyValues = state.dailyValues ? [...state.dailyValue] :[];
            }
            return {
                ...state,
                dailyValues : dailyValues
            };
        default:
            return state;
    }
}