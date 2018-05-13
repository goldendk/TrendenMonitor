import React from 'react';
import {Form, Checkbox, FormGroup, FormControl, Grid, Col, Row} from 'react-bootstrap'
import axios from 'axios';

//redux
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

class DailyValuesView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    componentDidMount() {
        window.backend.loadCompanies((response) => {
            this.setState({
                companies: response.data
            });
        });

    }

    onCbClick(event){

        console.log('cbclick', event.target.value);
        this.props.load

    }
    render() {
        this.counter = 0;
        if (!this.state.companies) {
            return <div>Loading data...</div>
        }


        const cols = 3;
        var rows = this.state.companies.length % cols;

        if (this.state.companies % cols != 0) {
            rows += 1;
        }

        var rows = [];

        this.state.companies.map((company, index) => {
            var itrRow = Math.floor(index / cols);
            if (!rows[itrRow]) {
                rows[itrRow] = [];
            }

            rows[itrRow].push((<Checkbox onClick={this.onCbClick} value={company.name} inline title={company.name}>{company.name}</Checkbox>));
        });

        return (<div>

            <Form>
                <Grid>

                    {rows.map((rowArr, rowArrIdx) => {
                        var rowCells = rowArr.map((cb, colArrIdx)=>{
                            return (<Col key={rowArrIdx + '_' + colArrIdx}  lg={1} md={2} xs={4}>{cb}</Col>)
                        });
                        return (<Row key={rowArrIdx}>{rowCells}</Row>);})
                    }
                </Grid>

            </Form>

        </div>)
    }
}

export default connect(null, null)(DailyValuesView);