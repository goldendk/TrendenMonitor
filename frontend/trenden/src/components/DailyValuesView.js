import React from 'react';
import {Form, Checkbox, FormGroup, FormControl, Grid, Col, Row} from 'react-bootstrap'
import axios from 'axios';
import {Line} from 'react-chartjs-2'

import {loadCompanyNames} from '/actions/index'

//redux
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

class DailyValuesView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedCompanies: [],
            chartData: {}
        }
    }


    componentDidMount() {
        window.backend.loadCompanies((response) => {
            this.setState({
                companies: response.data
            });
        });

    }

    onCbClick(event) {

        console.log('cbclick', event.target.value);
        this.setState({
            selectedCompanies: this.state.selectedCompanies.concat([event.target.value])
        });

        window.backend.loadDailyValues(this.state.selectedCompanies, function (data) {

            this.setState({chartData: data});

            console.log("DailyValuesView, Data loaded", data);
        }.bind(this));
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

            rows[itrRow].push((<Checkbox onClick={this.onCbClick.bind(this)} value={company.name} inline
                                         title={company.name}>{company.name}</Checkbox>));
        });


        const lineData = {
            datasets: [
                {
                    label: 'My First dataset',
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: 'rgba(75,192,192,0.4)',
                    borderColor: 'rgba(75,192,192,1)',
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: 'rgba(75,192,192,1)',
                    pointBackgroundColor: '#fff',
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: 'rgba(75,192,192,1)',
                    pointHoverBorderColor: 'rgba(220,220,220,1)',
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [65, 59, 80, 81, 56, 55, 40]
                }
            ]
        };

        const lineOptions = {

            title: "This is a test",
            xAxes: {
                title: "time",
                gridThickness: 2,
                unit: "day",
                unitStepSize: 86400,
                type: 'time',
                time: {
                    displayFormats: {
                        millisecond: 'MMM DD',
                        second: 'MMM DD',
                        minute: 'MMM DD',
                        hour: 'MMM DD',
                        day: 'MMM DD',
                        week: 'MMM DD',
                        month: 'MMM DD',
                        quarter: 'MMM DD',
                        year: 'MMM DD',
                    }
                }
            }
        };


        return (<div>

            <Form>
                <Grid>

                    {rows.map((rowArr, rowArrIdx) => {
                        var rowCells = rowArr.map((cb, colArrIdx) => {
                            return (<Col key={rowArrIdx + '_' + colArrIdx} lg={1} md={2} xs={4}>{cb}</Col>)
                        });
                        return (<Row key={rowArrIdx}>{rowCells}</Row>);
                    })
                    }</Grid>


                <div>
                    <Line data={lineData} />
                </div>


            </Form>
        </div>)

    }
}

export default connect(null, null)(DailyValuesView);