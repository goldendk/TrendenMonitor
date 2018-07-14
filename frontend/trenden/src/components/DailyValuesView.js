import React from 'react';
import {Form, Checkbox, FormGroup, FormControl, Grid, Col, Row} from 'react-bootstrap'
import axios from 'axios';
import {Line} from 'react-chartjs-2'

import {loadCompanyNames, loadDailyValues} from '../actions/index'
import {unselectCompany, selectCompany} from '../actions/dailyValuesActions'

//redux
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

class DailyValuesView extends React.Component {


    constructor(props) {
        super(props);
        this.COLORS = [
            'rgb(255, 99, 132)',
            'rgb(255, 159, 64)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)',
            'rgb(153, 102, 255)',
            'rgb(201, 203, 207)'
        ];
    }

    fetchColor(index) {
        return this.COLORS[index % this.COLORS.length];
    }


    componentDidMount() {
        this.props.loadCompanyNames();
    }

    onCbClick(event) {
        var companyName = event.target.value;
        if(!event.target.checked){
            this.props.unselectCompany(companyName);
        }
        else{
            this.props.selectCompany(companyName);
        }
    }


    render() {
        this.counter = 0;
        if (!this.props.companies) {
            return <div>Loading data...</div>
        }


        const cols = 3;
        var rows = this.props.companies.length % cols;

        if (this.props.companies % cols != 0) {
            rows += 1;
        }

        var rows = [];

        this.props.companies.map((company, index) => {
            var itrRow = Math.floor(index / cols);
            if (!rows[itrRow]) {
                rows[itrRow] = [];
            }

            rows[itrRow].push((<Checkbox onClick={this.onCbClick.bind(this)} value={company.name} inline
                                         title={company.name}>{company.name}</Checkbox>));
        });

        const lineData = {
            datasets: []
        };
        console.log("DailyValues: " + this.props.dailyValues);
        Object.entries(this.props.dailyValues).map((keyAndValuesArray) => {
           var idx = lineData.datasets.length;
            lineData.datasets.push(
                {
                    label: keyAndValuesArray[0],
                    backgroundColor: this.fetchColor(idx),
                    borderColor: this.fetchColor(idx),
                    fill: false,
                    data: []
                });


            keyAndValuesArray[1].map((dailyRecord)=>{
                lineData.datasets[idx].data.push({
                    x: new Date(dailyRecord.created),
                    y: dailyRecord.latestValue
                });
            });




        });


        const lineOptions = {
            responsive: true,
            title: {
                display: true,
                text: 'Daily values'
            },
            scales: {
                xAxes: [{
                    type: 'time',
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    },
                    ticks: {
                        major: {
                            fontStyle: 'bold',
                            fontColor: '#323334'
                        }
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'value'
                    }
                }]
            }
        };


        return (<div>

            <Form>
                <Grid>

                    {rows.map((rowArr, rowArrIdx) => {
                        var rowCells = rowArr.map((cb, colArrIdx) => {
                            return (<Col key={rowArrIdx + '_' + colArrIdx} lg={4} md={6} xs={12}>{cb}</Col>)
                        });
                        return (<Row key={rowArrIdx}>{rowCells}</Row>);
                    })
                    }</Grid>


                <div>
                    <Line data={lineData} options={lineOptions} redraw={lineData.datasets.length === 0 ? null : true} />
                </div>


            </Form>
        </div>)

    }
}

function mapStateToProps({chartData}, ownProps) {
    console.log("DailyValuesView.js, mapStateToProps", chartData);
    return {
        ...chartData
    }
}

export default connect(mapStateToProps, {loadCompanyNames, loadDailyValues, unselectCompany, selectCompany})(DailyValuesView);