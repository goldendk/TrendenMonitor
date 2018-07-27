import {RECOMMENDATION_PERIODS_LOADED } from "../actions/periodsActions";

export default function (state = {historicPotential: { records: []} }, action) {



    switch (action.type){

        case RECOMMENDATION_PERIODS_LOADED:
            var success = (action.payload.status === 200);

            if(success){
                return {...state, historicPotential: action.payload.data}
            }
            else{
                return state;
            }
        default:
            return state;
    }


}
