import {combineReducers} from 'redux';
import UserStateReducer from './reducer_user_state'
import ChartDataReducer from './reducer_chart_data'
import RecommendationPeriodReducer from './reducer_recommendation_period';
import UnderHiveReducer from './reducer_underhive';

const rootReducer = combineReducers({
    userState: UserStateReducer,
    chartData: ChartDataReducer,
    historicPotential: RecommendationPeriodReducer,
    underhiveState: UnderHiveReducer
});

export default rootReducer;
