import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import reduxPromise from 'redux-promise'
import reducers from './reducers';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import {BrowserRouter, HashRouter} from 'react-router-dom'
import MockBackend from './data/MockBackend'

import './index.css';



const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const createStoreWithMiddleware  = createStore(reducers, /* preloadedState, */ composeEnhancers(
    applyMiddleware(reduxPromise)
));
//const createStoreWithMiddleware = applyMiddleware(reduxPromise)(createStore);


ReactDOM.render(
    <Provider store={createStoreWithMiddleware}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </Provider>
    , document.getElementById('root'));
//FIXME: should this be enabled ?
//https://github.com/facebook/create-react-app/blob/master/packages/react-scripts/template/README.md#making-a-progressive-web-app
registerServiceWorker();
