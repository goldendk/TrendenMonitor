import {RECOMMENDATION_PERIODS_LOADED } from "../actions/periodsActions";

export default function (state = {recommendationPeriods: [] }, action) {



    switch (action.type){

        case RECOMMENDATION_PERIODS_LOADED:
            var success = (action.payload.status === 200);

            if(success){
                return {...state, recommendationPeriods: action.payload.data}
            }
            else{
                return state;
            }
        default:
            return state;
    }


}
