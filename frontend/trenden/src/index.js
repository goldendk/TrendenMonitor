import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import {BrowserRouter} from 'react-router-dom'
import Backend from './data/MockBackend'
window.backend = new Backend();
ReactDOM.render(

    <BrowserRouter>
    <App />
    </BrowserRouter>
    , document.getElementById('root'));
registerServiceWorker();
