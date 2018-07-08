import {combineReducers} from 'redux';
import UserStateReducer from './reducer_user_state'
import ChartDataReducer from './reducer_chart_data'


const rootReducer = combineReducers({
    userState: UserStateReducer,
    chartData: ChartDataReducer

});

export default rootReducer;
