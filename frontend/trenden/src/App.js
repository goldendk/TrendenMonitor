import React, { Component } from 'react';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import ButtonGroup  from 'react-bootstrap/lib/ButtonGroup'
import Button  from 'react-bootstrap/lib/Button'
import logo from './logo.svg';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React222222</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
          <Grid className="foo">

              <Row className="show-grid">
                  <Col xs={12} md={8}>
                      <code>&lt;{'Col xs={12} md={8}'} /">&gt;</code>
                  </Col>
                  <Col xs={6} md={4}>
                      <code>&lt;{'Col xs={6} md={4}'} /">&gt;</code>
                  </Col>
              </Row>

              <Row className="show-grid">
                  <Col xs={6} md={4}>
                      <code>&lt;{'Col xs={6} md={4}'} /">&gt;</code>
                  </Col>
                  <Col xs={6} md={4}>
                      <code>&lt;{'Col xs={6} md={4}'} /">&gt;</code>
                  </Col>
                  <Col xsHidden md={4}>
                      <code>&lt;{'Col xsHidden md={4}'} /">&gt;</code>
                  </Col>
              </Row>

              <Row className="show-grid">
                  <Col xs={6} xsOffset={6}>
                      <code>&lt;{'Col xs={6} xsOffset={6}'} /">&gt;</code>
                  </Col>
              </Row>

              <Row className="show-grid">
                  <Col md={6} mdPush={6}>
                      <code>&lt;{'Col md={6} mdPush={6}'} /">&gt;</code>
                  </Col>
                  <Col md={6} mdPull={6}>
                      <code>&lt;{'Col md={6} mdPull={6}'} /">&gt;</code>
                  </Col>
              </Row>
          </Grid>
          <ButtonGroup>
              <Button bsStyle="primary">Left</Button>
              <Button bsStyle="success">Middle</Button>
              <Button>Right</Button>
          </ButtonGroup>
      </div>
    );
  }
}

export default App;
