import {combineReducers} from 'redux';
import UserStateReducer from './reducer_user_state'


const rootReducer = combineReducers({
    userState: UserStateReducer

});

export default rootReducer;
