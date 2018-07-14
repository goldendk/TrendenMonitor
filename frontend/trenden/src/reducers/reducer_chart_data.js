import {CHART_COMPANIES_LOADED, CHART_DAILY_LOADED } from "../actions";
import {CHART_DAILY_UNSELECT_COMPANY, CHART_DAILY_SELECT_COMPANY } from "../actions/dailyValuesActions";

export default function (state = {dailyValues: {}, selectedCompanies:[] }, action) {




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

        case CHART_DAILY_SELECT_COMPANY:
            var newState = {...state};

            var dailyValues = {...state.dailyValues};
            if(action.payload.status === 200){
                action.payload.data.dataList.map((e) => {
                    dailyValues[e.name]  = dailyValues[e.name] || [];
                    dailyValues[e.name].push(e);
                });
            }

            newState.selectedCompanies = [...newState.selectedCompanies,action.meta.companyName];
            newState.dailyValues = dailyValues;
            return newState;

        case CHART_DAILY_UNSELECT_COMPANY:
            var newState = {...state};
            var companyName = action.payload;

            newState.selectedCompanies = newState.selectedCompanies.filter(e=> e !== companyName);
            delete newState.dailyValues[companyName];
            return newState;
        default:
            return state;
    }




}