import {combineReducers} from 'redux';
import UserStateReducer from './reducer_user_state'
import ChartDataReducer from './reducer_chart_data'
import RecommendationPeriodReducer from './reducer_recommendation_period';

const rootReducer = combineReducers({
    userState: UserStateReducer,
    chartData: ChartDataReducer,
    recommendationPeriods: RecommendationPeriodReducer
});

export default rootReducer;
