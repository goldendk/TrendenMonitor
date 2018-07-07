import React, {Component} from 'react';
import {Switch} from 'react'
import logo from './logo.svg';
import './App.css';
import MenuView from './components/MenuView'
import {Route} from 'react-router-dom'
import OpenRecommendationsView from './containers/OpenRecommendationsView'
import HistoricPotentialView from './components/HistoricPotentialView'
import DailyValuesView from './components/DailyValuesView'
import {Grid, Row, Col} from 'react-bootstrap'
import AuthView from "./components/authView"
import {GoogleLogout} from 'react-google-login'
class App extends Component {
    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Trenden Monitor</h1>

                </header>
                <MenuView/>

                <Grid>
                    <Row >
                        <Col  md={8} mdOffset={2}>
                            {/* Route to open recommenations*/}
                            <Route exact path='/open-recommendations-view' component={OpenRecommendationsView}/>


                            {/* Route to display historic potential*/}
                            <Route exact path='/historic-potential-view' component={HistoricPotentialView}/>

                            <Route exact path="/daily-values-view" component={DailyValuesView}/>

                            <Route exact path='/' component={AuthView}/>

                            {/*<section>*/}
                                {/*<p className="App-intro">*/}
                                    {/*To get started, edit <code>src/App.js</code> and save to reload.*/}
                                {/*</p>*/}
                            {/*</section>*/}
                        </Col>

                    </Row>
                </Grid>


            </div>
        );
    }
}

export default App;
