import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware} from 'redux';
import reduxPromise from 'redux-promise'
import reducers from './reducers';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import {BrowserRouter} from 'react-router-dom'
import Backend from './data/MockBackend'

import './index.css';

const createStoreWithMiddleware = applyMiddleware(reduxPromise)(createStore);

window.backend = new Backend();
ReactDOM.render(
    <Provider store={createStoreWithMiddleware(reducers)}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </Provider>
    , document.getElementById('root'));
registerServiceWorker();
